package org.pvytykac.paging;

import org.hamcrest.Matcher;
import org.pvytykac.core.matcher.GreaterThan;
import org.pvytykac.core.matcher.NotEqual;
import org.pvytykac.core.url.URL;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.not;

/**
  * @author paly
 * @since 17/11/2016 00:02
 */
// TODO: last page by ASC sort order + max value of offsetId
public class PagingSupportImpl implements PagingSupport {

    public static final int DEFAULT_PAGE_SIZE = 50;

    public static final String PAGE_PARAM = "page";
    public static final String OFFSET_ID_PARAM = "offsetId";
    public static final String NEXT_PARAM = "next";
    public static final String PREV_PARAM = "prev";
    public static final String OFFSET_PARAM = "offset";

    private static final Matcher<Object> NEDPS_MATCHER = new NotEqual(DEFAULT_PAGE_SIZE);
    private static final Matcher<Integer> GT0_MATCHER = new GreaterThan<>(0);
    private static final Matcher<Integer> GT1_MATCHER = new GreaterThan<>(1);

    private final String currentOffsetId;
    private final String nextOffsetId;
    private final String prevOffsetId;
    private final int currentPage;
    private final int totalSize;
    private final int pageSize;

    public PagingSupportImpl(String currentOffsetId, String nextOffsetId, String prevOffsetId, int currentPage, int totalSize, int pageSize) {
        this.currentOffsetId = currentOffsetId;
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
    public String getCurrentOffsetId() {
        return currentOffsetId;
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
            offsetId = getCurrentOffsetId();
            prev = getItemsOnPreviousPages() % newPageSize;
            next = newPageSize - prev;
        }
        return url(page, offsetId, next, prev, 0);
    }

    private URL url(int page, String offsetId, int next, int prev, int offset) {
        return new URL()
            .parameter(PAGE_PARAM, page, GT1_MATCHER)
            .parameter(OFFSET_ID_PARAM, offsetId, not(""))
            .parameter(NEXT_PARAM, next, allOf(GT0_MATCHER, NEDPS_MATCHER))
            .parameter(PREV_PARAM, prev, GT0_MATCHER)
            .parameter(OFFSET_PARAM, offset, GT0_MATCHER);
    }

}
