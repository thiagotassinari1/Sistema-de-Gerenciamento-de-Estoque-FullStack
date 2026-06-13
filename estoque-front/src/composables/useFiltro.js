import { reactive } from "vue";

export function useFiltro(estadoInicial) {
    const valoresIniciais = { ...estadoInicial }
    const filtros = reactive({ ...estadoInicial })

    function limpar() {
        Object.assign(filtros, valoresIniciais)
    }

    function aplicar(novosValores) {
        Object.assign(filtros, novosValores)
    }

    return {
        filtros,
        limpar,
        aplicar
    }
}