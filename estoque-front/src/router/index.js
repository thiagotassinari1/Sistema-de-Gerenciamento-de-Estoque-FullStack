import { createRouter, createWebHistory } from 'vue-router'
import ProdutoListaView from '../views/ProdutoListaView.vue'
import ProdutoFormView from '../views/ProdutoFormView.vue'

const routes = [
  
  { path: '/', redirect: '/produtos' },
  { path: '/produtos', name: 'ProdutosLista', component: ProdutoListaView },
  { path: '/produtos/novo', name: 'ProdutoNovo', component: ProdutoFormView },
  { path: '/produtos/:id/editar', name: 'ProdutoEditar', component: ProdutoFormView }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router