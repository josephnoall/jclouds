/**
 *
 * Copyright (C) 2010 Cloud Conscious, LLC. <info@cloudconscious.com>
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

package org.jclouds.googlestorage;

import org.jclouds.s3.S3ClientLiveTest;
import org.testng.annotations.Test;

/**
 * Tests behavior of {@code S3Client}
 * 
 * @author Adrian Cole
 */
@Test(groups = "live", sequential = true, testName = "GoogleStorageClientLiveTest")
public class GoogleStorageClientLiveTest extends S3ClientLiveTest {

}