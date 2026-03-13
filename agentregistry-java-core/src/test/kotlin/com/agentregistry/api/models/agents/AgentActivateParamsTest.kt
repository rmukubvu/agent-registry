// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.models.agents

import com.agentregistry.api.core.http.QueryParams
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class AgentActivateParamsTest {

    @Test
    fun create() {
        AgentActivateParams.builder()
            .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
            .queryApprovalReason("approvalReason")
            .queryApprovedBy("approvedBy")
            .queryIdemKey("idemKey")
            .bodyApprovalReason("approvalReason")
            .bodyApprovedBy("approvedBy")
            .bodyIdemKey("idemKey")
            .build()
    }

    @Test
    fun pathParams() {
        val params =
            AgentActivateParams.builder()
                .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .bodyApprovalReason("approvalReason")
                .bodyApprovedBy("approvedBy")
                .bodyIdemKey("idemKey")
                .build()

        assertThat(params._pathParam(0)).isEqualTo("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
        // out-of-bound path param
        assertThat(params._pathParam(1)).isEqualTo("")
    }

    @Test
    fun queryParams() {
        val params =
            AgentActivateParams.builder()
                .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .queryApprovalReason("approvalReason")
                .queryApprovedBy("approvedBy")
                .queryIdemKey("idemKey")
                .bodyApprovalReason("approvalReason")
                .bodyApprovedBy("approvedBy")
                .bodyIdemKey("idemKey")
                .build()

        val queryParams = params._queryParams()

        assertThat(queryParams)
            .isEqualTo(
                QueryParams.builder()
                    .put("approvalReason", "approvalReason")
                    .put("approvedBy", "approvedBy")
                    .put("idemKey", "idemKey")
                    .build()
            )
    }

    @Test
    fun queryParamsWithoutOptionalFields() {
        val params =
            AgentActivateParams.builder()
                .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .bodyApprovalReason("approvalReason")
                .bodyApprovedBy("approvedBy")
                .bodyIdemKey("idemKey")
                .build()

        val queryParams = params._queryParams()

        assertThat(queryParams).isEqualTo(QueryParams.builder().build())
    }

    @Test
    fun body() {
        val params =
            AgentActivateParams.builder()
                .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .queryApprovalReason("approvalReason")
                .queryApprovedBy("approvedBy")
                .queryIdemKey("idemKey")
                .bodyApprovalReason("approvalReason")
                .bodyApprovedBy("approvedBy")
                .bodyIdemKey("idemKey")
                .build()

        val body = params._body()

        assertThat(body.bodyApprovalReason()).isEqualTo("approvalReason")
        assertThat(body.bodyApprovedBy()).isEqualTo("approvedBy")
        assertThat(body.bodyIdemKey()).isEqualTo("idemKey")
    }

    @Test
    fun bodyWithoutOptionalFields() {
        val params =
            AgentActivateParams.builder()
                .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .bodyApprovalReason("approvalReason")
                .bodyApprovedBy("approvedBy")
                .bodyIdemKey("idemKey")
                .build()

        val body = params._body()

        assertThat(body.bodyApprovalReason()).isEqualTo("approvalReason")
        assertThat(body.bodyApprovedBy()).isEqualTo("approvedBy")
        assertThat(body.bodyIdemKey()).isEqualTo("idemKey")
    }
}
