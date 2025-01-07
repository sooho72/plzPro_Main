const apiClient = axios.create({
    baseURL: '/replies'
});

async function getList(replyId, page = 1, size = 10) {
    try {
        const result = await apiClient.get(`/${replyId}`, { params: { page, size } });
        return result.data;
    } catch (error) {
        console.error('Error fetching replies:', error);
        throw error;
    }
}

async function addReply(replyObj, postId) {
    try {
        const response = await apiClient.post(`/${postId}`, replyObj);
        return response.data;
    } catch (error) {
        console.error('Error adding reply:', error);
        throw error;
    }
}

async function addReReply(replyObj, parentId) {
    try {
        const response = await apiClient.post(`/${parentId}`, replyObj);
        return response.data;
    } catch (error) {
        console.error('Error adding reply:', error);
        throw error;
    }
}

async function getReply(replyId) {
    try {
        const response = await apiClient.get(`/${replyId}`);
        return response.data;
    } catch (error) {
        console.error('Error fetching reply:', error);
        throw error;
    }
}

async function modifyReply(replyId, replyObj) {
    try {
        const response = await apiClient.put(`/${replyId}`, replyObj);
        return response.data;
    } catch (error) {
        console.error('Error modifying reply:', error);
        throw error;
    }
}

async function removeReply(replyId) {
    try {
        const response = await apiClient.delete(`/${replyId}`);
        return response.data;
    } catch (error) {
        console.error('Error removing reply:', error);
        throw error;
    }
}
