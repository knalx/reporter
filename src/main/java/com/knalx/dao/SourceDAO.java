package com.knalx.dao;

import com.knalx.model.SourceLine;

/**
 * Created by knalx on 23.07.16.
 */
public interface SourceDAO {
    public SourceLine readNextLine();
}
