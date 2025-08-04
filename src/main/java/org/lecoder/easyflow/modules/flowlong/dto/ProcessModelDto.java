package org.lecoder.easyflow.modules.flowlong.dto;

import com.aizuda.bpm.engine.model.ProcessModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProcessModelDto extends ProcessModel implements Serializable {
    private static final long serialVersionUID = 1L;
}
