package req;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class PageReq {
    private int page;

    @NotNull(message = "each page cannot be null")
    @Max(value = 1000, message = "each page cannot exceed 1000")
    private int size;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PageReq{");
        sb.append("page=").append(page);
        sb.append(", size=").append(size);
        sb.append('}');
        return sb.toString();
    }
}