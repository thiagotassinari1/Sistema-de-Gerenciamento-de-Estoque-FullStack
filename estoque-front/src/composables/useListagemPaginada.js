import { computed, ref, reactive } from "vue";

export function useListagemPaginada(buscador, opcoes = {}) {

    const itens = ref([])
    const carregando = ref(false)
    const erro = ref('')

    const pagina = reactive({
        numero: 0,
        tamanho: opcoes.tamanhoInicial ?? 10,
        totalPaginas: 0,
        totalItens: 0,
        primeira: true,
        ultima: true,
    })

    const vazio = computed(() => {
        return !carregando.value && !erro.value && pagina.totalItens === 0
    })

    const numeroHumano = computed(() => {
        return pagina.totalPaginas === 0 ? 0 : pagina.numero + 1
    })

    async function carregar() {
        if (typeof buscador !== 'function') {
            throw new Error('useListagemPaginada: informe uma função de busca.')
        }

        carregando.value = true;
        erro.value = ''

        try {
            const dados = await buscador({
                page: pagina.numero,
                size: pagina.tamanho,
            })
            itens.value = Array.isArray(dados.content) ? dados.content : []
            pagina.totalPaginas = dados.totalPages ?? 0
            pagina.totalItens = dados.totalElements ?? 0
            pagina.primeira = dados.first ?? true
            pagina.ultima = dados.last ?? true
        } catch (e) {
            erro.value = 'Não foi possível carregar os dados. Verifique se o backend está no ar.'
            itens.value = []
            pagina.totalPaginas = 0
            pagina.totalItens = 0
            console.error(e)
        } finally {
            carregando.value = false
        }
    }

    function irParaPagina(numero) {
        if (numero < 0) return
        if (pagina.totalPaginas > 0 && numero > pagina.totalPaginas - 1) return
        pagina.numero = numero
        carregar()
    }

    function proxima() {
        if (!pagina.ultima) {
            irParaPagina(pagina.numero + 1)
        }
    }

    function anterior() {
        if (!pagina.primeira) {
            irParaPagina(pagina.numero - 1)
        }

    }

    function mudarTamanho(novoTamanho) {
        pagina.tamanho = Number(novoTamanho)
        pagina.numero = 0
        carregar()
    }

    function reiniciar() {
        pagina.numero = 0;
        carregar()
    }

    return {
        itens,
        carregando,
        erro,
        vazio,
        pagina,
        numeroHumano,
        carregar,
        irParaPagina,
        proxima,
        anterior,
        mudarTamanho,
        reiniciar,
    }
}