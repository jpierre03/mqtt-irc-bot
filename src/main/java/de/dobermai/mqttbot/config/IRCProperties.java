/*
 * Copyright 2013 Dominik Obermaier <dominik.obermaier@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.dobermai.mqttbot.config;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.netflix.governator.annotations.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;

/**
 * @author Dominik Obermaier
 */
@Singleton
public class IRCProperties {

    private static final Logger log = LoggerFactory.getLogger(IRCProperties.class);

    IRCProperties() {
        //Do not instantiate manually!
    }

    @Configuration("irc.hostname")
    private String ircHostName = "irc.freenode.net";

    @Configuration("irc.port")
    @Min(1)
    @Max(65535)
    private int port = 6667;

    @Configuration("irc.nickName")
    private String nickName = "mqtt_bot";

    @Configuration("irc.channels")
    private String ircChannelsRawString = "";


    private Iterable<String> ircChannels = new ArrayList<String>();

    @PostConstruct
    public void postConstruct() {
        //Governator does not allow property lists out of the box. See https://github.com/Netflix/governator/issues/74

        ircChannels = Splitter.on(",").omitEmptyStrings().trimResults().split(ircChannelsRawString);

        if (Iterables.isEmpty(ircChannels)) {
            log.warn("No IRC channels were set!");
        }
    }


    public String getIrcHostName() {
        return ircHostName;
    }

    public int getPort() {
        return port;
    }

    public String getNickName() {
        return nickName;
    }

    public Iterable<String> getIrcChannels() {
        return ircChannels;
    }
}
