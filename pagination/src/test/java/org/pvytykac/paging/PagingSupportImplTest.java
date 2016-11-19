package org.pvytykac.paging;

import org.junit.Test;
import org.pvytykac.core.url.URL;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author paly
 * @since 18/11/2016 22:04
 */
public class PagingSupportImplTest {

    public static final int NON_DEFAULT_PAGE_SIZE = 100;
    public static final int DEFAULT_PAGE_SIZE = 50;

    private static final TestCase[] TEST_CASES = new TestCase[]{
        new TestCase("Single page", new Expectations()
            .setCurrentOffsetId(null)
            .setNextOffsetId("10")
            .setPrevOffsetId("1")
            .setCurrentPage(1)
            .setTotalSize(NON_DEFAULT_PAGE_SIZE / 10)
            .setPageSize(NON_DEFAULT_PAGE_SIZE)
            .setTotalPages(1)
            .setItemsOnPreviousPages(0)
            .setItemsOnNextPages(0)
            .setViewRangeFrom(1)
            .setViewRangeTo(10)
            .setNextPage(false)
            .setPreviousPage(false)
            .setFirstPage(true)
            .setLastPage(true)
            .setNextPageURL(null)
            .setPreviousPageURL(null)
            .setFirstPageURL(buildURL(1, null, null, NON_DEFAULT_PAGE_SIZE, null))
            .setLastPageURL(buildURL(1, null, null, NON_DEFAULT_PAGE_SIZE, null))
            .addResizeURL(DEFAULT_PAGE_SIZE, buildURL(1, null, null, null, null))
            .addResizeURL(1, buildURL(1, null, null, 1, null))
        ),
        new TestCase("First page", new Expectations()
            .setCurrentOffsetId(null)
            .setNextOffsetId("100")
            .setPrevOffsetId("1")
            .setCurrentPage(1)
            .setTotalSize(NON_DEFAULT_PAGE_SIZE * 10)
            .setPageSize(NON_DEFAULT_PAGE_SIZE)
            .setTotalPages(10)
            .setItemsOnPreviousPages(0)
            .setItemsOnNextPages(900)
            .setViewRangeFrom(1)
            .setViewRangeTo(100)
            .setNextPage(true)
            .setPreviousPage(false)
            .setFirstPage(true)
            .setLastPage(false)
            .setNextPageURL(buildURL(2, "100", null, NON_DEFAULT_PAGE_SIZE, null))
            .setPreviousPageURL(null)
            .setFirstPageURL(buildURL(1, null, null, NON_DEFAULT_PAGE_SIZE, null))
            .setLastPageURL(buildURL(10, "100", 800, NON_DEFAULT_PAGE_SIZE, null))
        ),
        new TestCase("Middle page", new Expectations()
            .setCurrentOffsetId("500")
            .setNextOffsetId("600")
            .setPrevOffsetId("501")
            .setCurrentPage(6)
            .setTotalSize(NON_DEFAULT_PAGE_SIZE * 10)
            .setPageSize(NON_DEFAULT_PAGE_SIZE)
            .setTotalPages(10)
            .setItemsOnPreviousPages(500)
            .setItemsOnNextPages(400)
            .setViewRangeFrom(501)
            .setViewRangeTo(600)
            .setNextPage(true)
            .setPreviousPage(true)
            .setFirstPage(false)
            .setLastPage(false)
            .setNextPageURL(buildURL(7, "600", null, NON_DEFAULT_PAGE_SIZE, null))
            .setPreviousPageURL(buildURL(5, "501", null, null, NON_DEFAULT_PAGE_SIZE))
            .setFirstPageURL(buildURL(1, null, null, NON_DEFAULT_PAGE_SIZE, null))
            .setLastPageURL(buildURL(10, "600", 300, NON_DEFAULT_PAGE_SIZE, null))
            .addPageURL(3, buildURL(3, "501", NON_DEFAULT_PAGE_SIZE * 2, null, NON_DEFAULT_PAGE_SIZE))
            .addPageURL(4, buildURL(4, "501", NON_DEFAULT_PAGE_SIZE, null, NON_DEFAULT_PAGE_SIZE))
            .addPageURL(8, buildURL(8, "600", NON_DEFAULT_PAGE_SIZE, NON_DEFAULT_PAGE_SIZE, null))
            .addPageURL(9, buildURL(9, "600", NON_DEFAULT_PAGE_SIZE * 2, NON_DEFAULT_PAGE_SIZE, null))
            .addResizeURL(DEFAULT_PAGE_SIZE, buildURL(11, "500", null, null, null))
            .addResizeURL(150, buildURL(4, "500", null, 100, 50))
            .addResizeURL(NON_DEFAULT_PAGE_SIZE * 10, buildURL(1, null, null, NON_DEFAULT_PAGE_SIZE * 10, null))
        ),
        new TestCase("Last page", new Expectations()
            .setCurrentOffsetId("900")
            .setNextOffsetId("100")
            .setPrevOffsetId("901")
            .setCurrentPage(10)
            .setTotalSize(NON_DEFAULT_PAGE_SIZE * 10)
            .setPageSize(NON_DEFAULT_PAGE_SIZE)
            .setTotalPages(10)
            .setItemsOnPreviousPages(900)
            .setItemsOnNextPages(0)
            .setViewRangeFrom(901)
            .setViewRangeTo(1000)
            .setNextPage(false)
            .setPreviousPage(true)
            .setFirstPage(false)
            .setLastPage(true)
            .setNextPageURL(null)
            .setPreviousPageURL(buildURL(9, "901", null, null, NON_DEFAULT_PAGE_SIZE))
            .setFirstPageURL(buildURL(1, null, null, NON_DEFAULT_PAGE_SIZE, null))
            .setLastPageURL(null)
        ),
        new TestCase("Default page size", new Expectations()
            .setCurrentOffsetId("200")
            .setNextOffsetId("250")
            .setPrevOffsetId("201")
            .setCurrentPage(5)
            .setTotalSize(DEFAULT_PAGE_SIZE * 10)
            .setPageSize(DEFAULT_PAGE_SIZE)
            .setTotalPages(10)
            .setItemsOnPreviousPages(200)
            .setItemsOnNextPages(250)
            .setViewRangeFrom(201)
            .setViewRangeTo(250)
            .setNextPage(true)
            .setPreviousPage(true)
            .setFirstPage(false)
            .setLastPage(false)
            .setNextPageURL(buildURL(6, "250", null, null, null))
            .setPreviousPageURL(buildURL(4, "201", null, null, DEFAULT_PAGE_SIZE))
            .setFirstPageURL(buildURL(1, null, null, null, null))
            .setLastPageURL(buildURL(10, "250", DEFAULT_PAGE_SIZE * 4, null, null))
            .addPageURL(2, buildURL(2, "201", DEFAULT_PAGE_SIZE * 2, null, DEFAULT_PAGE_SIZE))
            .addPageURL(3, buildURL(3, "201", DEFAULT_PAGE_SIZE, null, DEFAULT_PAGE_SIZE))
            .addPageURL(7, buildURL(7, "250", DEFAULT_PAGE_SIZE, null, null))
            .addPageURL(8, buildURL(8, "250", DEFAULT_PAGE_SIZE * 2, null, null))
        ),
        new TestCase("Small last page", new Expectations()
            .setCurrentOffsetId("1000")
            .setNextOffsetId("1010")
            .setPrevOffsetId("1001")
            .setCurrentPage(2)
            .setTotalSize(1010)
            .setPageSize(1000)
            .setTotalPages(2)
            .setItemsOnPreviousPages(1000)
            .setItemsOnNextPages(0)
            .setViewRangeFrom(1001)
            .setViewRangeTo(1010)
            .setNextPage(false)
            .setPreviousPage(true)
            .setFirstPage(false)
            .setLastPage(true)
            .setNextPageURL(null)
            .setPreviousPageURL(buildURL(1, null, null, 1000, null))
            .setFirstPageURL(buildURL(1, null, null, 1000, null))
            .setLastPageURL(null)
            .addResizeURL(5, buildURL(201, "1000", null, 5, null))
            .addResizeURL(10, buildURL(101, "1000", null, 10, null))
            .addResizeURL(9, buildURL(112, "1000", null, 8, 1))
        )
    };

    @Test
    public void testItAll() throws Exception {
        for (TestCase tc : TEST_CASES)
            tc.test();
    }

    private static String buildURL(int page, String offsetId, Integer offset, Integer next, Integer prev) {
        StringBuilder sb = new StringBuilder("?");

        if (page > 1)
            sb.append(PagingSupportImpl.PAGE_PARAM)
              .append("=")
              .append(page)
              .append("&");

        if (offsetId != null && !offsetId.isEmpty())
            sb.append(PagingSupportImpl.OFFSET_ID_PARAM)
                .append("=")
                .append(offsetId)
                .append("&");

        if (next != null)
            sb.append(PagingSupportImpl.NEXT_PARAM)
                .append("=")
                .append(next)
                .append("&");

        if (prev != null)
            sb.append(PagingSupportImpl.PREV_PARAM)
                .append("=")
                .append(prev)
                .append("&");

        if (offset != null)
            sb.append(PagingSupportImpl.OFFSET_PARAM)
                .append("=")
                .append(offset)
                .append("&");

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private static class TestCase {

        private final String name;
        private final PagingSupportImpl ps;
        private final Expectations expectations;

        public TestCase(String name, Expectations expectations) {
            this.name = name;
            this.expectations = expectations;
            this.ps = new PagingSupportImpl(
                expectations.getCurrentOffsetId(), expectations.getNextOffsetId(), expectations.getPrevOffsetId(),
                expectations.getCurrentPage(), expectations.getTotalSize(), expectations.getPageSize()
            );
        }

        public void test() throws Exception {
            // Static values provided in constructor
            assertEquals(name, expectations.getCurrentOffsetId(), ps.getCurrentOffsetId());
            assertEquals(name, expectations.getNextOffsetId(), ps.getNextOffsetId());
            assertEquals(name, expectations.getPrevOffsetId(), ps.getPrevOffsetId());
            assertEquals(name, expectations.getCurrentPage(), ps.getCurrentPage());
            assertEquals(name, expectations.getPageSize(), ps.getPageSize());
            assertEquals(name, expectations.getTotalSize(), ps.getTotalSize());

            // Basic paging math
            assertEquals(name, expectations.getTotalPages(), ps.getTotalPages());
            assertEquals(name, expectations.getItemsOnPreviousPages(), ps.getItemsOnPreviousPages());
            assertEquals(name, expectations.getItemsOnNextPages(), ps.getItemsOnNextPages());
            assertEquals(name, expectations.getViewRangeFrom(), ps.getViewRangeFrom());
            assertEquals(name, expectations.getViewRangeTo(), ps.getViewRangeTo());

            // Boolean sensors
            assertEquals(name, expectations.isNextPage(), ps.hasNextPage());
            assertEquals(name, expectations.isPreviousPage(), ps.hasPreviousPage());
            assertEquals(name, expectations.isFirstPage(), ps.isFirstPage());
            assertEquals(name, expectations.isLastPage(), ps.isLastPage());

            // Resize URLs
            for (Map.Entry<Integer, String> pageSize : expectations.getResizeUrls().entrySet())
                assertEquals(name, pageSize.getValue(), ps.getResizeURL(pageSize.getKey()).toString());

            // Page URLs
            for (Map.Entry<Integer, String> page : expectations.getPageUrls().entrySet())
                assertEquals(name, page.getValue(), ps.getPageURL(page.getKey()).toString());

            URL nextPageURL = ps.getNextPageURL();
            URL prevPageURL = ps.getPreviousPageURL();
            URL lastPageURL = ps.getLastPageURL();
            assertEquals(name, expectations.getNextPageURL(), nextPageURL == null ? null : nextPageURL.toString());
            assertEquals(name, expectations.getPreviousPageURL(), prevPageURL == null ? null : prevPageURL.toString());
            assertEquals(name, expectations.getFirstPageURL(), ps.getFirstPageURL().toString());
            assertEquals(name, expectations.getLastPageURL(), lastPageURL == null ? null : lastPageURL.toString());

            if (expectations.getCurrentPage() != 1)
                assertNull(ps.getPageURL(expectations.getCurrentPage()));
        }
    }

    private static class Expectations {

        private final Map<Integer, String> pageUrls = new LinkedHashMap<>();
        private final Map<Integer, String> resizeUrls = new LinkedHashMap<>();

        private String currentOffsetId;
        private String nextOffsetId;
        private String prevOffsetId;
        private int currentPage;
        private int totalSize;
        private int pageSize;
        private int totalPages;
        private int itemsOnPreviousPages;
        private int itemsOnNextPages;
        private int viewRangeFrom;
        private int viewRangeTo;

        private boolean nextPage;
        private boolean previousPage;
        private boolean firstPage;
        private boolean lastPage;

        private String previousPageURL;
        private String nextPageURL;
        private String firstPageURL;
        private String lastPageURL;

        public Map<Integer, String> getPageUrls() {
            return pageUrls;
        }

        public Map<Integer, String> getResizeUrls() {
            return resizeUrls;
        }

        public Expectations addPageURL(int page, String url) {
            pageUrls.put(page, url);
            return this;
        }

        public Expectations addResizeURL(int pageSize, String url) {
            resizeUrls.put(pageSize, url);
            return this;
        }

        public String getCurrentOffsetId() {
            return currentOffsetId;
        }

        public Expectations setCurrentOffsetId(String currentOffsetId) {
            this.currentOffsetId = currentOffsetId;
            return this;
        }

        public String getNextOffsetId() {
            return nextOffsetId;
        }

        public Expectations setNextOffsetId(String nextOffsetId) {
            this.nextOffsetId = nextOffsetId;
            return this;
        }

        public String getPrevOffsetId() {
            return prevOffsetId;
        }

        public Expectations setPrevOffsetId(String prevOffsetId) {
            this.prevOffsetId = prevOffsetId;
            return this;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public Expectations setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
            return this;
        }

        public int getTotalSize() {
            return totalSize;
        }

        public Expectations setTotalSize(int totalSize) {
            this.totalSize = totalSize;
            return this;
        }

        public int getPageSize() {
            return pageSize;
        }

        public Expectations setPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public Expectations setTotalPages(int totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public int getItemsOnPreviousPages() {
            return itemsOnPreviousPages;
        }

        public Expectations setItemsOnPreviousPages(int itemsOnPreviousPages) {
            this.itemsOnPreviousPages = itemsOnPreviousPages;
            return this;
        }

        public int getItemsOnNextPages() {
            return itemsOnNextPages;
        }

        public Expectations setItemsOnNextPages(int itemsOnNextPages) {
            this.itemsOnNextPages = itemsOnNextPages;
            return this;
        }

        public int getViewRangeFrom() {
            return viewRangeFrom;
        }

        public Expectations setViewRangeFrom(int viewRangeFrom) {
            this.viewRangeFrom = viewRangeFrom;
            return this;
        }

        public int getViewRangeTo() {
            return viewRangeTo;
        }

        public Expectations setViewRangeTo(int viewRangeTo) {
            this.viewRangeTo = viewRangeTo;
            return this;
        }

        public boolean isNextPage() {
            return nextPage;
        }

        public Expectations setNextPage(boolean nextPage) {
            this.nextPage = nextPage;
            return this;
        }

        public boolean isPreviousPage() {
            return previousPage;
        }

        public Expectations setPreviousPage(boolean previousPage) {
            this.previousPage = previousPage;
            return this;
        }

        public boolean isFirstPage() {
            return firstPage;
        }

        public Expectations setFirstPage(boolean firstPage) {
            this.firstPage = firstPage;
            return this;
        }

        public boolean isLastPage() {
            return lastPage;
        }

        public Expectations setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
            return this;
        }

        public String getPreviousPageURL() {
            return previousPageURL;
        }

        public Expectations setPreviousPageURL(String previousPageURL) {
            this.previousPageURL = previousPageURL;
            return this;
        }

        public String getNextPageURL() {
            return nextPageURL;
        }

        public Expectations setNextPageURL(String nextPageURL) {
            this.nextPageURL = nextPageURL;
            return this;
        }

        public String getFirstPageURL() {
            return firstPageURL;
        }

        public Expectations setFirstPageURL(String firstPageURL) {
            this.firstPageURL = firstPageURL;
            return this;
        }

        public String getLastPageURL() {
            return lastPageURL;
        }

        public Expectations setLastPageURL(String lastPageURL) {
            this.lastPageURL = lastPageURL;
            return this;
        }
    }
}
