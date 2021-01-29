package com.redhat.examples.guice;

import com.redhat.pam.bdd.context.BDDContext;
import com.redhat.pam.guice.BDDCustomContextProvider;
import com.redhat.pam.runtime.BDDEmbeddedRuntime;
import org.jbpm.services.task.identity.JBossUserGroupCallbackImpl;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.task.UserGroupCallback;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;

import java.util.Properties;

public class ContextProvider implements BDDCustomContextProvider {
    @Override
    public BDDContext get() {
//        final String[] GAV = System.getProperty("kjar.deployment.gav").split(":");
        final String[] GAV = "com.redhat.pam.bdd.examples:project-to-test-with-bdd:1.0".split(":");//System.getProperty("kjar.deployment.gav").split(":");

        final KieServices ks = KieServices.Factory.get();
        final ReleaseId releaseId = ks.newReleaseId(GAV[0], GAV[1], GAV[2]);

        final BDDEmbeddedRuntime embeddedRuntime = new BDDEmbeddedRuntime() {
            @Override
            public UserGroupCallback getUserGroupCallback() {
                final Properties properties = new Properties();
                properties.put("BankEmployee", "BANK.EMPLOYEE");
                return new JBossUserGroupCallbackImpl(properties);
            }
        };

        return new BDDContext(embeddedRuntime.getRuntimeManager(releaseId), ProcessInstanceIdContext.get());
    }
}
