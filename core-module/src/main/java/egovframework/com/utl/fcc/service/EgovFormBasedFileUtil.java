package egovframework.com.utl.fcc.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cmm.util.EgovResourceCloseHelper;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EgovFormBasedFileUtil {
	/** Buffer size */
	public static final int BUFFER_SIZE = 20480;

	public static final String SEPERATOR = File.separator;

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovFormBasedFileUtil.class);

	public static String getTodayString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

		return format.format(new Date());
	}

	public static String getPhysicalFileName() {
		return EgovFormBasedUUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

	protected static String convert(String filename) throws Exception {
		//return java.net.URLEncoder.encode(filename, "utf-8");
		return MimeUtility.encodeText( filename, "utf-8", "B" );
	}

	public static long saveFile(InputStream is, File file) throws IOException {
		// 디렉토리 생성
		if (!file.getParentFile().exists()) {
			//2017.03.03 	조성원 	시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
			if(file.getParentFile().mkdirs()){
				LOGGER.debug("[file.mkdirs] file : Directory Creation Success");
			}else{				
				LOGGER.error("[file.mkdirs] file : Directory Creation Fail");
			}
		}

		OutputStream os = null;
		long size = 0L;

		try {
			os = new FileOutputStream(file);

			int bytesRead = 0;
			byte[] buffer = new byte[BUFFER_SIZE];

			while ((bytesRead = is.read(buffer, 0, BUFFER_SIZE)) != -1) {
				size += bytesRead;
				os.write(buffer, 0, bytesRead);
			}
		} finally {
			EgovResourceCloseHelper.close(os);
		}

		return size;
	}

	public static List<EgovFormBasedFileVo> uploadFiles(HttpServletRequest request, String where, long maxFileSize) throws Exception {
		List<EgovFormBasedFileVo> list = new ArrayList<EgovFormBasedFileVo>();

		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if (isMultipart) {
			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload();
			upload.setFileSizeMax(maxFileSize); // SizeLimitExceededException

			// Parse the request
			FileItemIterator iter = upload.getItemIterator(request);
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				String name = item.getFieldName();
				InputStream stream = item.openStream();
				if (item.isFormField()) {
					LOGGER.info("Form field '{}' with value '{}' detected.", name, Streams.asString(stream));
				} else {
					LOGGER.info("File field '{}' with file name '{}' detected.", name, item.getName());

					if ("".equals(item.getName())) {
						continue;
					}

					// Process the input stream
					EgovFormBasedFileVo vo = new EgovFormBasedFileVo();

					String tmp = item.getName();

					if (tmp.lastIndexOf("\\") >= 0) {
						tmp = tmp.substring(tmp.lastIndexOf("\\") + 1);
					}

					vo.setFileName(tmp);
					vo.setContentType(item.getContentType());
					vo.setServerSubPath(getTodayString());
					vo.setPhysicalName(getPhysicalFileName());

					if (tmp.lastIndexOf(".") >= 0) {
						vo.setPhysicalName(vo.getPhysicalName() + tmp.substring(tmp.lastIndexOf(".")));
					}

					long size = saveFile(stream, new File(EgovWebUtil.filePathBlackList(where) + SEPERATOR + vo.getServerSubPath() + SEPERATOR + vo.getPhysicalName()));

					vo.setSize(size);

					list.add(vo);
				}
			}
		} else {
			throw new IOException("form's 'enctype' attribute have to be 'multipart/form-data'");
		}

		return list;
	}

	public static void downloadFile(HttpServletResponse response, String where, String serverSubPath, String physicalName, String original) throws Exception {
		String contentType = "application/octet-stream";
		downloadFile(response, where, serverSubPath, physicalName, contentType ,original);
	}

	public static void downloadFile(HttpServletResponse response, String where, String serverSubPath, String physicalName, String contentType ,String original) throws Exception {
		String downFileName = where + SEPERATOR + serverSubPath + SEPERATOR + physicalName;

		File file = new File(EgovWebUtil.filePathBlackList(downFileName));

		if (!file.exists()) {
			throw new FileNotFoundException(downFileName);
		}

		if (!file.isFile()) {
			throw new FileNotFoundException(downFileName);
		}

		byte[] b = new byte[BUFFER_SIZE];

		original = original.replaceAll("\r", "").replaceAll("\n", "");
		response.setContentType(contentType);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + convert(original) + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");

		BufferedInputStream fin = null;
		BufferedOutputStream outs = null;

		try {
			fin = new BufferedInputStream(new FileInputStream(file));
			outs = new BufferedOutputStream(response.getOutputStream());

			int read = 0;

			while ((read = fin.read(b)) != -1) {
				outs.write(b, 0, read);
			}
		} finally {
			EgovResourceCloseHelper.close(outs, fin);
		}
	}

	public static void viewFile(HttpServletResponse response, String where, String serverSubPath, String physicalName, String mimeTypeParam) throws Exception {
		String mimeType = mimeTypeParam;
		String downFileName = where + SEPERATOR + serverSubPath + SEPERATOR + physicalName;

		File file = new File(EgovWebUtil.filePathBlackList(downFileName));

		if (!file.exists()) {
			throw new FileNotFoundException(downFileName);
		}

		if (!file.isFile()) {
			throw new FileNotFoundException(downFileName);
		}

		byte[] b = new byte[BUFFER_SIZE];

		if (mimeType == null) {
			mimeType = "application/octet-stream;";
		}

		response.setContentType(EgovWebUtil.removeCRLF(mimeType));
		response.setHeader("Content-Disposition", "filename=image;");

		BufferedInputStream fin = null;
		BufferedOutputStream outs = null;

		try {
			fin = new BufferedInputStream(new FileInputStream(file));
			outs = new BufferedOutputStream(response.getOutputStream());

			int read = 0;

			while ((read = fin.read(b)) != -1) {
				outs.write(b, 0, read);
			}
		} finally {
			EgovResourceCloseHelper.close(outs, fin);
		}
	}
}
