package com.langrsoft.pi.pantry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertThat;

public class AnItemRetrieverWithSpecificApiPropertyKey {
    String currentApiKey;

    @Before
    public void saveCurrentApiKey() {
        currentApiKey = System.getProperty(ItemRetriever.UPC_API_KEY_PROPERTY_NAME);
    }

    @After
    public void resetCurrentApiKeyToSaved() {
        if (currentApiKey != null)
            System.setProperty(ItemRetriever.UPC_API_KEY_PROPERTY_NAME, currentApiKey);
    }

    @Test
    public void constructsUrlUsingSystemPropertyForApiKey() {
        System.setProperty(ItemRetriever.UPC_API_KEY_PROPERTY_NAME, "SOME_API_KEY");
        ItemRetriever retriever = new ItemRetriever(null);

        String url = retriever.url("123");

        assertThat(url, endsWith("SOME_API_KEY/123"));
    }
}
