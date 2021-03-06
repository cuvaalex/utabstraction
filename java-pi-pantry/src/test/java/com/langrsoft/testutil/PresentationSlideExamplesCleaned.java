package com.langrsoft.testutil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.CharBuffer;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static com.langrsoft.util.LambdaUtil.*;

public class PresentationSlideExamplesCleaned {

    @Test
    public void someHappyPathBehaviorWorks() throws IOException {
        doSomethingThatShouldWorkUsing(someGoodData());

        assertThat(somethingWorkedAsExpected(), is(true));
    }

    private void doSomethingThatShouldWorkUsing(Object o) throws IOException {
    }

    private boolean somethingWorkedAsExpected() {
        return true;
    }

    private Object someGoodData() {
        return null;
    }


    Schema schema = new Schema();

    class CGSchema {
    }

    class Schema {
    }

    class Partition {
        private CGSchema[] CGSchemas = {new CGSchema()};

        public Partition(String s, String strStorage, Object o) throws Exception {
            if (!strStorage.equals(""))
                throw new RuntimeException("Encountered \" <IDENTIFIER> \"xyz \"\" at line 1, column 23.");
        }

        public CGSchema[] getCGSchemas() {
            return CGSchemas;
        }
    }

    @Test
    public void testStorageValid3() throws Exception {
        String strStorage = "";
        Partition p = new Partition(schema.toString(), strStorage, null);
        CGSchema[] cgschemas = p.getCGSchemas();
        Assert.assertEquals(cgschemas.length, 1);
    }

    @Test
    public void testStorageInvalid7() {
        try {
            String strStorage = "[f1, f2] serialize by xyz compress by gz; [f3, f4] SERIALIZE BY avro COMPRESS BY lzo";
            Partition p = new Partition(schema.toString(), strStorage, null);
            p.getCGSchemas();
        } catch (Exception e) {
            String errMsg = e.getMessage();
            String str = "Encountered \" <IDENTIFIER> \"xyz \"\" at line 1, column 23.";
            assertEquals(errMsg.startsWith(str), true);
        }
    }


    class TestLogHandler {
        private Level level;

        public void setLevel(Level level) {
            this.level = level;
        }
    }

    static class Logger {
        public void addHandler(TestLogHandler handler) {
        }
    }

    interface DataReader {
        GCModel read();
    }

    class DataReaderJRockit1_6_0 implements DataReader {
        public DataReaderJRockit1_6_0(InputStream in) {
        }

        @Override
        public GCModel read() {
            return new GCModel();
        }
    }

    static Logger IMP_LOGGER = new Logger();
    static Logger DATA_READER_FACTORY_LOGGER = new Logger();

    class GCModel {
        public int size() {
            return 42;
        }

        public GCEvent get(int i) {
            return new GCEvent(24.930);
        }
    }

    class GCEvent {
        private double timestamp;

        public GCEvent(double timestamp) {
            this.timestamp = timestamp;
        }

        public double getTimestamp() {
            return timestamp;
        }
    }

    @Before
    public void suppressLoggingBelowWarning() {
        TestLogHandler handler = new TestLogHandler();
        handler.setLevel(Level.WARNING);
        IMP_LOGGER.addHandler(handler);
        DATA_READER_FACTORY_LOGGER.addHandler(handler);
    }

    @Test
    public void testGcPrioPauseSingleParCon() throws Exception {
        InputStream in = getInputStream("SampleJRockit1_6_gc_mode_singleparcon.txt");
        DataReader reader = new DataReaderJRockit1_6_0(in);
        GCModel model = reader.read();

        assertEquals("count", 42, model.size());

        GCEvent event = (GCEvent) model.get(0);
        assertEquals("timestamp", 24.930, event.getTimestamp(), 0.000001);
        // ...
    }

    private InputStream getInputStream(String s) {
        return new ByteArrayInputStream(s.getBytes());
    }

    class KickstartWorkflow {
        private String name;
        private String description;

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void addTask(KickstartMailTask task) {
        }
    }

    class KickstartMailTask {
        private String id;
        private String name;
        private String description;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Container getTo() {
            return new Container();
        }

        public Container getSubject() {
            return new Container();
        }

        public Container getHtml() {
            return new Container();
        }

        public Container getText() {
            return new Container();
        }
    }

    class Container {

        private String expression;
        private String stringValue;

        public void setExpression(String expression) {
            this.expression = expression;
        }

        public void setStringValue(String stringValue) {
            this.stringValue = stringValue;
        }
    }

    class MarshallingService {
        public String marshallWorkflow(KickstartWorkflow dto) {
            return "serviceTask :type=\"mail\"";
        }
    }

    MarshallingService marshallingService = new MarshallingService();

    private KickstartWorkflow dto = new KickstartWorkflow();
    private KickstartMailTask task;

    @Test
    public void testEmailTaskWithSomeFieldsOnly() throws Exception {
        task = new KickstartMailTask();
        task.setId("myId");
        task.getTo().setStringValue("you@yourcompany.com");
        task.getSubject().setStringValue("my subject string");
        task.getHtml().setExpression("${my.html.expression}");
        task.getText().setStringValue("my text string");
        dto.addTask(task);

        String bpmn = marshallingService.marshallWorkflow(dto);

        assertTrue(bpmn.indexOf("serviceTask ") > -1);
        assertTrue(bpmn.indexOf(":type=\"mail\"") > -1);
        // ... nothing mentioning "one mail task"
    }

    class TaskBuilder {
        private String name = "some default value";
        private String id = "someDefaultId";
        private String to = "x@example.com";
        private String subject = "my subject string";
        private String text = "my text string";
        private String html = "<html/>";

        public TaskBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public TaskBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public TaskBuilder withTo(String to) {
            this.to = to;
            return this;
        }

        public TaskBuilder withSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public TaskBuilder withHtml(String html) {
            this.html = html;
            return this;
        }

        public TaskBuilder withText(String text) {
            this.text = text;
            return this;
        }

        KickstartMailTask create() {
            KickstartMailTask task = new KickstartMailTask();
            task.setName(name);
            task.getTo().setStringValue(to);
            task.getSubject().setStringValue(subject);
            task.getHtml().setExpression(html);
            task.getText().setStringValue(text);
            return task;
        }
    }

    @Test
    public void populatesTemplateWithEmailAddress() throws Exception {
        task = new TaskBuilder().withHtml("${my.html.expression}").create();
        dto.addTask(task);

        String bpmn = marshallingService.marshallWorkflow(dto);

        assertTrue(containsExpression(bpmn, "${my.html.expression}"));
    }

    private boolean containsExpression(String bpmn, String s) {
        return true;
    }

    class OftenContentDao {
        private List<OftenContent> data = new ArrayList<>();

        public void createOrUpdate(long contentId, String username, long timestamp, long score) {
            if (!contains(contentId, username))
                data.add(new OftenContent(contentId, username, timestamp, score));
            else
                update(contentId, username, timestamp, score);
        }

        private void update(long contentId, String username, long timestamp, long score) {
            OftenContent content = get(contentId, username);
            content.lastViewTimestamp = timestamp;
            content.score += score;
        }

        private OftenContent get(long contentId, String username) {
            return data.stream()
                    .filter(oc -> oc.contentId == contentId && oc.username.equals(username))
                    .findFirst().get();
        }

        private boolean contains(long contentId, String username) {
            return data.stream().anyMatch(oc -> oc.contentId == contentId && oc.username.equals(username));
        }

        public List<OftenContent> findOftenContent(String username) {
            return data.stream().filter(oc -> oc.username.equals(username))
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());
        }
    }

    OftenContentDao dao = new OftenContentDao();

    class ActiveObject {
        public void flush() {
        }
    }

    ActiveObject ao = new ActiveObject();

    class OftenContent implements Comparable<OftenContent> {
        private long score;
        private long contentId;
        private String username;
        private long lastViewTimestamp;

        public OftenContent(long contentId, String username, long timestamp, long score) {
            this.contentId = contentId;
            this.username = username;
            this.lastViewTimestamp = timestamp;
            this.score = score;
        }

        public long getScore() {
            return score;
        }

        public long getContentId() {
            return contentId;
        }

        @Override
        public int compareTo(OftenContent that) {
            if (this.score < that.score) return -1;
            if (this.score > that.score) return 1;
            return 0;
        }
    }

    static final int SOME_CONTENT_ID = 0;
    int startOfTimeInMS = 0;

    @Test
    public void createOrUpdateShouldUpdateScore() throws Exception {
        dao.createOrUpdate(SOME_CONTENT_ID, "test", startOfTimeInMS, 10);
        dao.createOrUpdate(SOME_CONTENT_ID, "test", ++startOfTimeInMS, 20);
        ao.flush();

        List<OftenContent> oftenContents = dao.findOftenContent("test");
        assertThat(oftenContents.size(), is(1));
        assertThat(oftenContents.get(0).getScore(), is(30L));
    }

    @Test
    public void createOrUpdateShouldUpdateScore2() throws Exception {
        dao.createOrUpdate(SOME_CONTENT_ID, "test", startOfTimeInMS, 10);
        dao.createOrUpdate(SOME_CONTENT_ID, "test", ++startOfTimeInMS, 20);
        ao.flush();

        List<OftenContent> oftenContents = dao.findOftenContent("test");
        assertThat(oftenContents.size(), is(1));
        assertThat(oftenContents.get(0).getScore(), is(30L));
    }

    @Test
    public void findOftenContentSortByDescendingScore() throws Exception {
        dao.createOrUpdate(SOME_CONTENT_ID, "test", startOfTimeInMS, 1);
        dao.createOrUpdate(SOME_CONTENT_ID + 1, "test", ++startOfTimeInMS, 2);
        dao.createOrUpdate(SOME_CONTENT_ID + 2, "test", ++startOfTimeInMS, 3);
        ao.flush();

        List<OftenContent> results = dao.findOftenContent("test");

        assertThat(map(results, OftenContent::getScore), is(equalTo(asList(3L, 2L, 1L))));
    }

    private List<Long> scores(List<OftenContent> results) {
        return results.stream().map(OftenContent::getScore).collect(toList());
    }


    static class Repository {
    }

    static class SesameTripleRepositoryFactory {
        public static Repository createInMemoryRepository() {
            return new Repository();
        }
    }

    static class SesameConceptRepositoryFactory {
        public static ObjectRepository createConceptRepository(Repository repository, Set<Class<?>> concepts, String s) {
            return new ObjectRepository();
        }
    }

    static class ObjectRepository {
    }

    class User {
    }

    class Project {
    }

    class SesameConceptRepository {
        public SesameConceptRepository(ObjectRepository objectRepository) {
        }
    }

    @Test
    public void testGetURI() {
        Repository repository = SesameTripleRepositoryFactory.createInMemoryRepository();
        Assert.assertNotNull(repository);
        Set<Class<?>> concepts = new HashSet<Class<?>>();
        concepts.add(User.class);
        concepts.add(Project.class);
        ObjectRepository objectRepository = SesameConceptRepositoryFactory
                .createConceptRepository(repository, concepts, "http://...");
        Assert.assertNotNull(objectRepository);
        SesameConceptRepository conceptRepository = new SesameConceptRepository(
                objectRepository);
        // ...
    }

    @Test
    public void testGetUri() {
        Repository repository = SesameTripleRepositoryFactory.createInMemoryRepository();
        Set<Class<?>> concepts = new HashSet<Class<?>>();
        concepts.add(User.class);
        concepts.add(Project.class);
        ObjectRepository objectRepository = SesameConceptRepositoryFactory
                .createConceptRepository(repository, concepts, "http://...");
        SesameConceptRepository conceptRepository = new SesameConceptRepository(
                objectRepository);
        // ...
    }

    class TestIntRangeManager {
        public List<Integer> mConfigList = new ArrayList<>();
//        {
//            mConfigList.add(1);
//        }
    }

    @Test
    public void configListIsEmptyOnCreation() {
        TestIntRangeManager testManager = new TestIntRangeManager();
        assertEquals(0, testManager.mConfigList.size());
    }

    @Test
    public void configListIsEmptyOnCreation2() {
        TestIntRangeManager testManager = new TestIntRangeManager();
        assertThat(testManager.mConfigList, is(empty()));
    }

    class HttpServletRequestImpl {
        private Map<String, String> params = new HashMap<>();

        public String getParameter(String key) {
            return params.get(key);
        }

        public void addParams(String paramsString) {
            for (String pair : paramsString.split("&")) {
                String[] keyValue = pair.split("=");
                String key = keyValue[0];
                if (!params.containsKey(key)) {
                    String value = decode(keyValue[1]);
                    params.put(key, value);
                }
            }
        }

        private String decode(String s) {
            return s.replace("%20", " ");
        }

        public void post(String url, String body) {
            String[] postPathParts = url.split("\\?");
            if (postPathParts.length > 1) {
                String postPathParams = postPathParts[1];
                addParams(postPathParams);
            }
            addParams(body);
        }

        public void execute(String content) {
            String[] contentParts = content.split("\r\n");
            String body = contentParts[contentParts.length - 1];
            String postCommand = contentParts[0];
            String postPath = postCommand.split(" ")[1];
            // assume only posts for now
            post(postPath, body);
        }
    }

    private HttpServletRequestImpl request = new HttpServletRequestImpl();

    String someHeaders(int length) {
        return "Date: Tue, 01 May 2012 06:46:45 GMT \r\n" +
               "Connection: close \r\n" +
               "Host: www.myfavouriteamazingsite.com\r\n" +
               "From: joebloe@somewebsitesomewhere.com \r\n" +
               "Accept: text/html, text/plain \r\n" +
               "Cookie: $Version=\"1\"; Customer=\"WILE_E_COYOTE\"; $Path=\"/acme\"; \r\n" +
               "User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1) \r\n" +
               "Accept-Language: en-US, jp \r\n" +
               "Accept-Language: cn \r\n" +
               "Content-Length: " + length + "\r\n" +
               "Content-Type: application/x-www-form-urlencoded;charset=UTF-8\r\n" + "\r\n";
    }

    @Test
    public void postStoresParametersFromBody() {
        String body = "param1=value1&param2=value2";

        request.execute("POST /somepath HTTP/1.1 \r\n"
                        + someHeaders(body.length())
                        + body);

        assertEquals(request.getParameter("param1"), "value1");
        assertEquals(request.getParameter("param2"), "value2");
    }

    @Test
    public void postStoresParametersFromUrlAlso() {
        String body = "param1=value1";

        request.execute("POST /somepath?urlParam1=urlValue1 HTTP/1.1 \r\n"
                        + someHeaders(body.length())
                        + body);

        assertEquals(request.getParameter("param1"), "value1");
        assertEquals(request.getParameter("urlParam1"), "urlValue1");
    }

    @Test
    public void postDecodesParamValues() {
        String body = "param1=some%20value";

        request.execute("POST /somepath HTTP/1.1 \r\n"
                        + someHeaders(body.length())
                        + body);

        assertEquals(request.getParameter("param1"), "some value");
    }

    @Test
    public void postIgnoresSubsequentDuplicateParamValue() {
        String body = "param1=firstValue&param1=laterValue";

        request.execute("POST /somepath HTTP/1.1 \r\n"
                        + someHeaders(body.length())
                        + body);

        assertEquals(request.getParameter("param1"), "firstValue");
    }

    static class EncounterService {
        public Encounter getEncounter(int i) {
            return new Encounter();
        }

        public void saveEncounter(Encounter encounter) {
            encounter.getObs().setObsId("1");
        }
    }

    static class Context {
        private static EncounterService encounterService;

        public static EncounterService getEncounterService() {
            return new EncounterService();
        }
    }

    static class Encounter {
        private LocalDateTime encounterDatetime = LocalDateTime.now();
        private Obs obs;

        public LocalDateTime getEncounterDatetime() {
            return encounterDatetime;
        }

        public void addObs(Obs obs) {
            this.obs = obs;
        }

        public Obs getObs() {
            return obs;
        }
    }

    class Obs {
        private double valueNumeric;
        private Date obsDatetime;
        private String obsId;

        public void setValueNumeric(double valueNumeric) {
            this.valueNumeric = valueNumeric;
        }

        public void setObsDatetime(Date obsDatetime) {
            this.obsDatetime = obsDatetime;
        }

        public String getObsId() {
            return obsId;
        }

        public void setObsId(String obsId) {
            this.obsId = obsId;
        }
    }

    private EncounterService service;

    @Before
    public void createEncounterService() {
        service = Context.getEncounterService();
    }

    @Test
    public void saveCascadesToContainedObsForExistingEncounter() {
        Encounter encounter = loadEncounterFromService();
        encounter.addObs(createObsWithId(null));

        service.saveEncounter(encounter);

        assertNotNull(encounter.getObs().getObsId());
    }

    private Encounter loadEncounterFromService() {
        return service.getEncounter(1);
    }

    private Obs createObsWithId(String id) {
        Obs obs = new Obs();
        obs.setObsId(null);
        obs.setValueNumeric(50d);
        obs.setObsDatetime(new Date());
        return obs;
    }

    private Pattern pattern;

    public void nonCapturingConstrWithFlags() {
        pattern = Pattern.compile("(?i)b*(?-i)a*");
        assertTrue(pattern.matcher("bBbBaaaa").matches());
        assertFalse(pattern.matcher("bBbBAaAa").matches());
    }

    public void nonCapturingConstrWithGroups() {
        pattern = Pattern.compile("(?i:b*)a*");
        assertTrue(pattern.matcher("bBbBaaaa").matches());
        assertFalse(pattern.matcher("bBbBAaAa").matches());
    }

    public void nonCapturingConstrWithPositiveLookahead() {
        pattern = Pattern.compile(".*\\.(?=log$).*$");
        assertTrue(pattern.matcher("a.b.c.log").matches());
        assertFalse(pattern.matcher("a.b.c.log.").matches());
    }

    @Test
    public void test_Reader_CharBuffer_ZeroChar() throws IOException {
        MockReader mockReader = new MockReader("test string".toCharArray());
        assertEquals(0, mockReader.read(CharBuffer.allocate(0)));

        char[] buffer = new char["test string".length()];
        mockReader.read(buffer);
        assertEquals("test string", String.valueOf(buffer));
    }

    class MockReader extends Reader {
        private char[] contents;
        private int current_offset = 0;
        private int length = 0;

        public MockReader() {
            super();
        }

        public MockReader(char[] data) {
            contents = data;
            length = contents.length;
        }

        @Override
        public void close() throws IOException {
            contents = null;
        }

        @Override
        public int read(char[] buf, int offset, int count) throws IOException {
            if (null == contents) {
                return -1;
            }
            if (length <= current_offset) {
                return -1;
            }
            if (buf.length < offset + count) {
                throw new IndexOutOfBoundsException();
            }
            count = Math.min(count, length - current_offset);
            for (int i = 0; i < count; i++) {
                buf[offset + i] = contents[current_offset + i];
            }
            current_offset += count;
            return count;
        }

    }

    class CentroidProcess {
        public SimpleFeatureCollection execute(ListFeatureCollection fc) {
            SimpleFeatureCollection c = new SimpleFeatureCollection();
            c.add(new SimpleFeature(0, 0, "one"));
            c.add(new SimpleFeature(10, 0, "two"));
            return c;
        }
    }

    class Point {
        private double x;
        private double y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    class SimpleFeatureCollection {
        List<SimpleFeature> features = new ArrayList<>();

        public SimpleFeatureIterator features() {
            return new SimpleFeatureIterator(features);
        }

        public void add(SimpleFeature simpleFeature) {
            features.add(simpleFeature);
        }
    }

    class SimpleFeatureIterator implements Iterator<SimpleFeature> {
        private Iterator<SimpleFeature> featuresIterator;
        private List<SimpleFeature> features;

        public SimpleFeatureIterator(List<SimpleFeature> features) {
            this.features = features;
            featuresIterator = features.iterator();
        }

        @Override
        public boolean hasNext() {
            return featuresIterator.hasNext();
        }

        @Override
        public SimpleFeature next() {
            return featuresIterator.next();
        }
    }

    class SimpleFeature {
        private Object defaultGeometry;
        private String name;

        public SimpleFeature(int x, int y, String name) {
            this.name = name;
            this.defaultGeometry = new Point(x, y);
        }

        public Object getDefaultGeometry() {
            return defaultGeometry;
        }

        public String getAttribute(String key) {
            return name;
        }

        @Override
        public String toString() {
            return "(" + ((Point) defaultGeometry).getX() + "," + ((Point) defaultGeometry).getY() + ")" + name;
        }
    }

    class ListFeatureCollection {
    }

    ListFeatureCollection fc = new ListFeatureCollection();

    @Test
    public void testResults() throws Exception {
        CentroidProcess cp = new CentroidProcess();
        SimpleFeatureCollection result = cp.execute(fc);

        SimpleFeatureIterator it = result.features();
        assertTrue(it.hasNext());
        SimpleFeature f = it.next();
        assertEquals(0, ((Point) f.getDefaultGeometry()).getX(), 1e-6);
        assertEquals(0, ((Point) f.getDefaultGeometry()).getY(), 1e-6);
        assertEquals("one", f.getAttribute("name"));
        f = it.next();
        assertEquals(10, ((Point) f.getDefaultGeometry()).getX(), 1e-6);
        assertEquals(0, ((Point) f.getDefaultGeometry()).getY(), 1e-6);
        assertEquals("two", f.getAttribute("name"));
    }

    class StopPlaatsController {
        public String addStopPlaats(StopPlaats sp, Object o, int tripId) {
            return "";
        }

        public ModelAndView updateStopPlaatsPage(MockHttpServletRequest mockHttpServletRequest, Object o, String stopPlaatsID) {
            return new ModelAndView();
        }
    }

    StopPlaatsController stopPlaatsController = new StopPlaatsController();

    class ModelAndView {
        public String getViewName() {
            return "StopPlaats/updateStopPlaats";
        }
    }

    class Trip {
        private ArrayList<StopPlaats> stopPlaatsen;
        private int tripId;

        public void setStopPlaatsen(ArrayList<StopPlaats> stopPlaatsen) {
            this.stopPlaatsen = stopPlaatsen;
        }

        public ArrayList<StopPlaats> getStopPlaatsen() {
            return stopPlaatsen;
        }

        public int getTripId() {
            return tripId;
        }
    }

    class TripDAO {
        public void addTrip(Trip t) {
        }
    }

    class StopPlaats {
        private String stopPlaatsID;

        public String getStopPlaatsID() {
            return stopPlaatsID;
        }
    }

    class MockHttpServletRequest {

    }

    TripDAO tripDAO = new TripDAO();

    @Test
    public void testUpdateStopPlaatsPage() {
        Trip trip = new Trip();
        trip.setStopPlaatsen(new ArrayList<StopPlaats>());
        tripDAO.addTrip(trip);
        StopPlaats stopPlaats = getStopPlaats();
        trip.getStopPlaatsen().add(stopPlaats);
        stopPlaatsController.addStopPlaats(stopPlaats, null, trip.getTripId());

        ModelAndView modelAndView = stopPlaatsController.updateStopPlaatsPage(
                new MockHttpServletRequest(), null, stopPlaats.getStopPlaatsID());

        assertEquals("StopPlaats/updateStopPlaats", modelAndView.getViewName());
    }

    public StopPlaats getStopPlaats() {
        return new StopPlaats();
    }
}
