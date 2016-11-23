package org.pvytykac.paging;

import org.apache.commons.lang3.StringUtils;
import org.pvytykac.core.url.URL;

/**
  * @author paly
 * @since 17/11/2016 00:02
 */
// TODO: last page by ASC sort order + max value of offsetId
public class PagingSupportImpl implements PagingSupport {

    private final String nextOffsetId;
    private final String prevOffsetId;
    private final int currentPage;
    private final int totalSize;
    private final int pageSize;

    public PagingSupportImpl(String nextOffsetId, String prevOffsetId, int currentPage, int totalSize, int pageSize) {
        this.nextOffsetId = nextOffsetId;
        this.prevOffsetId = prevOffsetId;
        this.currentPage = currentPage;
        this.totalSize = totalSize;
        this.pageSize = pageSize;
    }

    @Override
    public int getCurrentPage() {
        return currentPage;
    }

    @Override
    public int getTotalSize() {
        return totalSize;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public String getNextOffsetId() {
        return nextOffsetId;
    }

    @Override
    public String getPrevOffsetId() {
        return prevOffsetId;
    }

    @Override
    public int getTotalPages() {
        return (int) Math.ceil((double) totalSize / (double) pageSize);
    }

    @Override
    public boolean hasNextPage() {
        return getCurrentPage() < getTotalPages();
    }

    @Override
    public boolean hasPreviousPage() {
        return getCurrentPage() > 1;
    }

    @Override
    public boolean isLastPage() {
        return getCurrentPage() == getTotalPages();
    }

    @Override
    public boolean isFirstPage() {
        return getCurrentPage() == 1;
    }

    @Override
    public int getItemsOnPreviousPages() {
        return getPageSize() * (getCurrentPage() - 1);
    }

    @Override
    public int getItemsOnNextPages() {
        return Math.max(0, getTotalSize() - getItemsOnPreviousPages() - getPageSize());
    }

    @Override
    public int getViewRangeFrom() {
        return getItemsOnPreviousPages() + 1;
    }

    @Override
    public int getViewRangeTo() {
        return Math.min(getTotalSize(), getItemsOnPreviousPages() + getPageSize());
    }

    @Override
    public boolean pageExists(int page) {
        return page > 0 && page <= getTotalPages();
    }

    @Override
    public URL getNextPageURL() {
        return getPageURL(getCurrentPage() + 1);
    }

    @Override
    public URL getPreviousPageURL() {
        return getPageURL(getCurrentPage() - 1);
    }

    @Override
    public URL getFirstPageURL() {
        return getPageURL(1);
    }

    @Override
    public URL getLastPageURL() {
        return getPageURL(getTotalPages());
    }

    @Override
    public URL getPageURL(int page) {
        if (!pageExists(page))
            return null;

        int next = 0;
        int prev = 0;
        String offsetId;
        int offset = (Math.abs(getCurrentPage() - page) - 1) * getPageSize();

        if (page == 1) {
            next = getPageSize();
            offsetId = "";
            offset = 0;
        } else if (page > getCurrentPage()) {
            next = getPageSize();
            offsetId = nextOffsetId;
        } else if (page < getCurrentPage()) {
            prev = getPageSize();
            offsetId = prevOffsetId;
        } else {
            return null;
        }

        return url(page, offsetId, next, prev, offset);
    }

    @Override
    public URL getResizeURL(int newPageSize) {
        int page = (int) Math.ceil((double) getViewRangeFrom() / (double) newPageSize);
        int next, prev;
        String offsetId;

        if (page == 1) {
            offsetId = "";
            prev = 0;
            next = newPageSize;
        } else {
            offsetId = getPrevOffsetId();
            prev = getItemsOnPreviousPages() % newPageSize;
            next = newPageSize - prev;
        }
        return url(page, offsetId, next, prev, 0);
    }

    private URL url(int page, String offsetId, int next, int prev, int offset) {
        boolean inclusive = StringUtils.isNotEmpty(offsetId) && page > 1 && next > 0 && prev > 0;
        return URL.url()
            .parameter(PAGE_PARAM, page, page > 1)
            .parameter(OFFSET_ID_PARAM, offsetId, StringUtils.isNotEmpty(offsetId))
            .parameter(NEXT_PARAM, next, next > 0 && (next != DEFAULT_PAGE_SIZE || prev > 0))
            .parameter(PREV_PARAM, prev, prev > 0)
            .parameter(OFFSET_PARAM, offset, offset > 0)
            .parameter(INCLUSIVE_PARAM, inclusive, inclusive);
    }

}