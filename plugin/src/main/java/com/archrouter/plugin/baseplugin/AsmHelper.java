package com.archrouter.plugin.baseplugin;

import java.io.IOException;

public interface AsmHelper {
    byte[] modifyClass(byte[] srcClass) throws IOException;
}
