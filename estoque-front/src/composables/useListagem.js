import { computed, ref } from 'vue'

export function useListagem(carregadorPadrao) {
    const itens = ref([])
    const carregando = ref(false)
    const erro = ref('')

    const vazio = computed(() => !carregando.value && !erro.value && itens.value.length === 0)
    const total = computed(() => itens.value.length)

    async function carregar() {
        carregando.value = true
        erro.value = ''
        try {
            const dados = await carregadorPadrao()
            itens.value = Array.isArray(dados) ? dados : []
        } catch (e) {
            erro.value = 'Não foi possível carregar os dados. Verifique se o backend está no ar.'
            itens.value = []
            console.error(e)
        } finally {
            carregando.value = false
        }
    }

    return { itens, carregando, erro, vazio, total, carregar }
}
