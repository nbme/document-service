package org.nbme.wbti.document.model;

/**
 * Created by rwang on 11/23/2015.
 */
import javax.persistence.*;

@Entity
@Table(name = "DocumentProcess")
public class DocumentProcess {
    @Id @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "targete")
    private String targetUrl;

    @Column(name = "source")
    private String sourceUrl;

   private DocumentAction action;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public DocumentAction getAction() {
        return action;
    }

    public void setAction(DocumentAction action) {
        this.action = action;
    }
}