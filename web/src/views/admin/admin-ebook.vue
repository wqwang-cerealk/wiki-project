<template>
  <a-layout>
    <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">
      <a-table
        :columns="columns"
        :row-key="record => record.id"
        :data-source="ebooks"
        :pagination="pagination"
        :loading="loading"
        @change="handleTableChange"
      >
      <template #cover="{ text: cover }">
          <img v-if="cover" :src="cover" alt="avatar" />
      </template>
      <template v-slot:action="{ text, record }">
        <a-space size="small">
        <a-button type="primary">
          edit
        </a-button>
        <a-button type="danger">
          delete
        </a-button>
        </a-space>
      </template>
    </a-table>
    </a-layout-content>
  </a-layout>
</template>

<script lang="ts">
  import {defineComponent, onMounted, ref} from 'vue';
  import axios from "axios";

  export default defineComponent({
    name: 'AdminEbook',
    setup() {
      const ebooks = ref();
      const pagination = ref({
        current: 1,
        pageSize: 4,
        total: 0
      });
      const loading = ref(false);

      const columns = [
        {
          title: 'cover',
          dataIndex: 'cover',
          slots: {customRender: 'cover'}
        },
        {
          title: 'name',
          dataIndex: 'name'
        },
        {
          title: 'category1',
          key: 'category1Id',
          dataIndex: 'category1Id'
        },
        {
          title: 'category2',
          dataIndex: 'category2Id'
        },
        {
          title: 'document count',
          dataIndex: 'docCount'
        },
        {
          title: 'view count',
          dataIndex: 'viewCount'
        },
        {
          title: 'vote count',
          dataIndex: 'voteCount'
        },
        {
          title: 'action',
          key: 'action',
          slots: {customerRender: 'action'}
        }
      ];

      /**
       * 数据查询
       **/
      const handleQuery = (params: any) => {
        loading.value = true;
        axios.get("/ebook/list", {
          params: {
            page: params.page,
            size: params.size
          }
        }).then((response) => {
          loading.value = false;
          const data = response.data;
          ebooks.value = data.content.list;

      // 重置分页按钮
          pagination.value.current = params.page;
          pagination.value.total = data.content.total;
        });
      };

      /**
       * 表格点击页码时触发
       */
      const handleTableChange = (pagination: any) => {
        console.log("看看自带的分页参数都有啥：" + pagination);
        handleQuery({
          page: pagination.current,
          size: pagination.pageSize
        });
      };

      onMounted(() => {
        handleQuery({
          page: 1,
          size: pagination.value.pageSize
        });
      });
      return {
        ebooks,
        pagination,
        columns,
        loading,
        handleTableChange
      }
    }
  });
</script>

<style scoped>
  img {
    width: 50px;
    height: 50px;
  }
</style>