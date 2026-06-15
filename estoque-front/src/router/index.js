import { createRouter, createWebHistory } from 'vue-router'
import ProdutoListaView from '../views/ProdutoListaView.vue'
import ProdutoFormView from '../views/ProdutoFormView.vue'
import CategoriaListaView from '../views/CategoriaListaView.vue'
import CategoriaFormView from '../views/CategoriaFormView.vue'
import FornecedorListaView from '../views/FornecedorListaView.vue'
import FornecedorFormView from '../views/FornecedorFormView.vue'
import PedidoListaView from '../views/PedidoListaView.vue'
import PedidoFormView from '../views/PedidoFormView.vue'

const routes = [
  { path: '/', redirect: '/produtos' },
  { path: '/produtos', name: 'ProdutosLista', component: ProdutoListaView },
  { path: '/produtos/novo', name: 'ProdutoNovo', component: ProdutoFormView },
  { path: '/produtos/:id/editar', name: 'ProdutoEditar', component: ProdutoFormView },
  { path: '/categorias', name: 'CategoriasLista', component: CategoriaListaView },
  { path: '/categorias/nova', name: 'CategoriaNova', component: CategoriaFormView },
  { path: '/categorias/:id/editar', name: 'CategoriaEditar', component: CategoriaFormView },
  { path: '/fornecedores', name: 'FornecedoresLista', component: FornecedorListaView },
  { path: '/fornecedores/novo', name: 'FornecedorNovo', component: FornecedorFormView },
  { path: '/fornecedores/:id/editar', name: 'FornecedorEditar', component: FornecedorFormView },
  { path: '/pedidos', name: 'PedidosLista', component: PedidoListaView },
  { path: '/pedidos/novo', name: 'PedidoNovo', component: PedidoFormView },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
