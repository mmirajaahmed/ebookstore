package com.ebookstore.controller;

import com.ebookstore.model.Order;
import com.ebookstore.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void getOrderHistory_ExistingMember_ReturnsOrders() throws Exception {
        mockMvc.perform(get("/api/v1/orders/member/M001")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getOrderHistory_NonExistentMember_ReturnsEmptyArray() throws Exception {
        mockMvc.perform(get("/api/v1/orders/member/NON_EXISTENT")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getOrderHistory_WithNullCreatedAtOrder_DoesNotThrow500() throws Exception {
        Order nullDateOrder = new Order();
        nullDateOrder.setId("ORD_TEST_NULL_DATE");
        nullDateOrder.setMemberId("M_TEST_NULL");
        nullDateOrder.setStatus("PENDING");
        nullDateOrder.setCreatedAt(null);
        orderRepository.save(nullDateOrder);
        // Force createdAt to null in store list directly to test comparator resilience
        orderRepository.findById("ORD_TEST_NULL_DATE").ifPresent(o -> o.setCreatedAt(null));

        mockMvc.perform(get("/api/v1/orders/member/M_TEST_NULL")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value("ORD_TEST_NULL_DATE"));
    }

    @Test
    void getAllOrders_ReturnsOkAndList() throws Exception {
        mockMvc.perform(get("/api/v1/orders")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void placeOrder_WithoutMemberId_DoesNotThrow500() throws Exception {
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"items\":[]}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.status").value("PENDING"));
    }
}
