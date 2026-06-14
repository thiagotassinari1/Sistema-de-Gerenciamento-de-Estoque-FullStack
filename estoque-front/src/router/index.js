import { createRouter, createWebHistory } from 'vue-router'
import ProdutoListaView from '../views/ProdutoListaView.vue'
import ProdutoFormView from '../views/ProdutoFormView.vue'
import CategoriaListaView from '../views/CategoriaListaView.vue'
import CategoriaFormView from '../views/CategoriaFormView.vue'

const routes = [
  { path: '/', redirect: '/produtos' },
  { path: '/produtos', name: 'ProdutosLista', component: ProdutoListaView },
  { path: '/produtos/novo', name: 'ProdutoNovo', component: ProdutoFormView },
  { path: '/produtos/:id/editar', name: 'ProdutoEditar', component: ProdutoFormView },
  { path: '/categorias', name: 'CategoriasLista', component: CategoriaListaView },
  { path: '/categorias/nova', name: 'CategoriaNova', component: CategoriaFormView },
  { path: '/categorias/:id/editar', name: 'CategoriaEditar', component: CategoriaFormView },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router