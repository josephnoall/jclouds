/**
 *
 * Copyright (C) 2009 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

package org.jclouds.aws.sqs.config;

import static com.google.common.util.concurrent.Executors.sameThreadExecutor;
import static org.testng.Assert.assertEquals;

import java.net.URI;
import java.util.Map;

import org.jclouds.Constants;
import org.jclouds.aws.domain.Region;
import org.jclouds.aws.handlers.AWSClientErrorRetryHandler;
import org.jclouds.aws.handlers.AWSRedirectionRetryHandler;
import org.jclouds.aws.handlers.ParseAWSErrorFromXmlContent;
import org.jclouds.aws.sqs.reference.SQSConstants;
import org.jclouds.concurrent.config.ExecutorServiceModule;
import org.jclouds.http.functions.config.ParserModule;
import org.jclouds.http.handlers.DelegatingErrorHandler;
import org.jclouds.http.handlers.DelegatingRetryHandler;
import org.jclouds.util.Jsr330;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;

/**
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "sqs.SQSRestClientModuleTest")
public class SQSRestClientModuleTest {

   Injector createInjector() {
      return Guice.createInjector(new SQSRestClientModule(), new ExecutorServiceModule(
               sameThreadExecutor(), sameThreadExecutor()), new ParserModule(),
               new AbstractModule() {
                  @Override
                  protected void configure() {
                     bindConstant().annotatedWith(
                              Jsr330.named(SQSConstants.PROPERTY_AWS_ACCESSKEYID)).to("user");
                     bindConstant().annotatedWith(
                              Jsr330.named(SQSConstants.PROPERTY_AWS_SECRETACCESSKEY)).to("key");
                     bindConstant().annotatedWith(
                              Jsr330.named(SQSConstants.PROPERTY_SQS_ENDPOINT_US_EAST_1)).to(
                              "http://default");
                     bindConstant().annotatedWith(
                              Jsr330.named(SQSConstants.PROPERTY_SQS_ENDPOINT_US_WEST_1)).to(
                              "http://uswest");
                     bindConstant().annotatedWith(
                              Jsr330.named(SQSConstants.PROPERTY_SQS_ENDPOINT_EU_WEST_1)).to(
                              "http://euwest");
                     bindConstant().annotatedWith(
                              Jsr330.named(SQSConstants.PROPERTY_AWS_EXPIREINTERVAL)).to(30);
                     bindConstant().annotatedWith(
                              Jsr330.named(Constants.PROPERTY_IO_WORKER_THREADS)).to("1");
                     bindConstant().annotatedWith(Jsr330.named(Constants.PROPERTY_USER_THREADS))
                              .to("1");
                  }
               });
   }

   @Test
   void testServerErrorHandler() {
      DelegatingErrorHandler handler = createInjector().getInstance(DelegatingErrorHandler.class);
      assertEquals(handler.getServerErrorHandler().getClass(), ParseAWSErrorFromXmlContent.class);
   }

   @Test
   void testRegions() {
      Map<Region, URI> regionMap = createInjector().getInstance(new Key<Map<Region, URI>>() {
      });
      assertEquals(regionMap, ImmutableMap.<Region, URI> of(Region.US_EAST_1, URI
               .create("http://default"), Region.US_WEST_1, URI.create("http://uswest"),
               Region.EU_WEST_1, URI.create("http://euwest")));
   }

   @Test
   void testClientErrorHandler() {
      DelegatingErrorHandler handler = createInjector().getInstance(DelegatingErrorHandler.class);
      assertEquals(handler.getClientErrorHandler().getClass(), ParseAWSErrorFromXmlContent.class);
   }

   @Test
   void testClientRetryHandler() {
      DelegatingRetryHandler handler = createInjector().getInstance(DelegatingRetryHandler.class);
      assertEquals(handler.getClientErrorRetryHandler().getClass(),
               AWSClientErrorRetryHandler.class);
   }

   @Test
   void testRedirectionRetryHandler() {
      DelegatingRetryHandler handler = createInjector().getInstance(DelegatingRetryHandler.class);
      assertEquals(handler.getRedirectionRetryHandler().getClass(),
               AWSRedirectionRetryHandler.class);
   }

}