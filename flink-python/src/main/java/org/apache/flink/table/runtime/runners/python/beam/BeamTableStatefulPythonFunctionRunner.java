/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.table.runtime.runners.python.beam;

import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.fnexecution.v1.FlinkFnApi;
import org.apache.flink.python.env.PythonEnvironmentManager;
import org.apache.flink.python.metric.FlinkMetricContainer;
import org.apache.flink.runtime.memory.MemoryManager;
import org.apache.flink.runtime.state.KeyedStateBackend;
import org.apache.flink.table.types.logical.RowType;

import java.util.Map;

/** A {@link BeamTableStatefulPythonFunctionRunner} used to execute Python stateful functions. */
public class BeamTableStatefulPythonFunctionRunner extends BeamTablePythonFunctionRunner {

    private final FlinkFnApi.UserDefinedAggregateFunctions userDefinedAggregateFunctions;

    public BeamTableStatefulPythonFunctionRunner(
            String taskName,
            PythonEnvironmentManager environmentManager,
            RowType inputType,
            RowType outputType,
            String functionUrn,
            FlinkFnApi.UserDefinedAggregateFunctions userDefinedFunctions,
            Map<String, String> jobOptions,
            FlinkMetricContainer flinkMetricContainer,
            KeyedStateBackend keyedStateBackend,
            TypeSerializer keySerializer,
            TypeSerializer namespaceSerializer,
            MemoryManager memoryManager,
            double managedMemoryFraction,
            FlinkFnApi.CoderParam.DataType inputDataType,
            FlinkFnApi.CoderParam.DataType outputDataType,
            FlinkFnApi.CoderParam.OutputMode outputMode) {
        super(
                taskName,
                environmentManager,
                inputType,
                outputType,
                functionUrn,
                jobOptions,
                flinkMetricContainer,
                keyedStateBackend,
                keySerializer,
                namespaceSerializer,
                memoryManager,
                managedMemoryFraction,
                inputDataType,
                outputDataType,
                outputMode);
        this.userDefinedAggregateFunctions = userDefinedFunctions;
    }

    @Override
    protected byte[] getUserDefinedFunctionsProtoBytes() {
        return this.userDefinedAggregateFunctions.toByteArray();
    }
}
