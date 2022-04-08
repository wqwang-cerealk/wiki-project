<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <p>
        <a-form layout="inline" :model="param">
          <a-form-item>
            <a-button type="primary" @click="handleQuery()">
              search
            </a-button>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="add()">
              create
            </a-button>
          </a-form-item>
        </a-form>
      </p>
      <a-table
          :columns="columns"
          :row-key="record => record.id"
          :data-source="level1"
          :loading="loading"
          :pagination="false"
      >
        <template #cover="{ text: cover }">
          <img v-if="cover" :src="cover" alt="avatar" />
        </template>
        <template v-slot:action="{ text, record }">
          <a-space size="small">
            <a-button type="primary" @click="edit(record)">
              edit
            </a-button>
            <a-popconfirm
                title="Are you sure you want to delete this book?"
                ok-text="Yes"
                cancel-text="No"
                @confirm="del(record.id)"
            >
              <a-button type="danger">
                delete
              </a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>
  <a-modal
      title="分类表单"
      v-model:visible="modalVisible"
      :confirm-loading="modalLoading"
      @ok="handleModalOk"
  >
    <a-form :model="category" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <a-form-item label="Name">
        <a-input v-model:value="category.name" />
      </a-form-item>
      <a-form-item label="Sort">
        <a-input v-model:value="category.sort" />
      </a-form-item>
      <a-form-item label="Parent Category">
        <a-input v-model:value="category.parent" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import axios from 'axios';
import { message } from 'ant-design-vue';
import {Tool} from "@/util/tool";

export default defineComponent({
  name: 'AdminCategory',
  setup() {
    const param = ref();
    param.value = {};
    const categorys = ref();
    const loading = ref(false);

    const columns = [
      {
        title: 'Name',
        dataIndex: 'name'
      },
      {
        title: 'Parent Category',
        key: 'parent',
        dataIndex: 'parent'
      },
      {
        title: 'Sort',
        dataIndex: 'sort'
      },
      {
        title: 'Action',
        key: 'action',
        slots: { customRender: 'action' }
      }
    ];

    /**
     * level 1 tree, children is level 2 categorization
     * [{
     *   id: "",
     *   name: "",
     *   children: [{
     *     id: "",
     *     name: "",
     *   }]
     * }]
     *
     **/
    const level1 = ref();



    /**
     * 数据查询
     **/
    const handleQuery = () => {
      loading.value = true;
      axios.get("/category/all").then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success) {
            categorys.value = data.content;
            console.log("原始数组", categorys.value);

            level1.value = [];
            level1.value = Tool.arrayToTree(categorys.value, 0);
            console.log("树型结构", level1);
          } else {
            message.error(data.message);
          }
      });
    };

    // -------- 表单 ---------
    const category = ref({});
    const modalVisible = ref(false);
    const modalLoading = ref(false);
    const handleModalOk = () => {
      modalLoading.value = true;
      axios.post("/category/save", category.value).then((response) => {
        modalLoading.value = false;
        const data = response.data; //data == CommonResp
        if (data.success) {
          modalVisible.value = false;
          //重新加载列表
          handleQuery();
        } else {
          message.error(data.message);
        }
      });
    };

    /**
     * edit
     */
    const edit = (record: any) => {
      modalVisible.value = true;
      category.value = Tool.copy(record);
    };

    /**
     * add new category
     */
    const add = () => {
      modalVisible.value = true;
      category.value = {};
    }

    const del = (id: number) => {
      axios.delete("/category/delete/" + id).then((response) => {
        const data = response.data; //data == CommonResp
        if (data.success) {
          //重新加载列表
          handleQuery();
        }
      });
    }

    onMounted(() => {
      handleQuery();
    });

    onMounted(() => {
      handleQuery();
    });

    return {
      param,
      // categorys,
      level1,
      columns,
      loading,
      handleQuery,

      edit,
      add,

      category,
      modalVisible,
      modalLoading,
      handleModalOk,

      del
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

