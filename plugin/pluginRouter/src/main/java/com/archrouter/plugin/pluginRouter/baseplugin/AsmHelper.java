package com.archrouter.plugin.pluginRouter.baseplugin;

import java.io.IOException;

public interface AsmHelper {
    byte[] modifyClass(byte[] srcClass) throws IOException;
}
