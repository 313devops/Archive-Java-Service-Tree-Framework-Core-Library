package egovframework.com.utl.fcc.service;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EgovFormBasedFileVo implements Serializable {
    /** 파일명 */
    private String fileName = "";
    /** ContextType */
    private String contentType = "";
    /** 하위 디렉토리 지정 */
    private String serverSubPath = "";
    /** 물리적 파일명 */
    private String physicalName = "";
    /** 파일 사이즈 */
    private long size = 0L;

    /** jQuery FileUpload 플러그인을 위한 추가 필드 **/
    private String name = "";
    private String url = "";
    private String thumbnailUrl = "";
    private String delete_url = "";
    private String delete_type = "DELETE";
    
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public String getServerSubPath() {
        return serverSubPath;
    }
    public void setServerSubPath(String serverSubPath) {
        this.serverSubPath = serverSubPath;
    }
    public String getPhysicalName() {
        return physicalName;
    }
    public void setPhysicalName(String physicalName) {
        this.physicalName = physicalName;
    }
    public long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }

    public String getName() {
        return fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getDelete_url() {
        return delete_url;
    }

    public void setDelete_url(String delete_url) {
        this.delete_url = delete_url;
    }

    public String getDelete_type() {
        return delete_type;
    }

    public void setDelete_type(String delete_type) {
        this.delete_type = delete_type;
    }
}
