package at.petrak.hexcasting.api.casting.mishaps

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.GarbageIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.pigment.FrozenPigment
import net.minecraft.network.chat.Component
import net.minecraft.world.item.DyeColor

class MishapForEachBelowWaterLevel(val expected: Int, val got: Int, val acc: List<Iota>) : Mishap() {
    override fun accentColor(
        ctx: CastingEnvironment,
        errorCtx: Context
    ): FrozenPigment = dyeColor(DyeColor.BLUE)

    override fun execute(
        env: CastingEnvironment,
        errorCtx: Context,
        stack: MutableList<Iota>
    ) {
        env.mishapEnvironment.drown()
        repeat(expected - got) { stack.add(GarbageIota.INSTANCE) }
        stack.addAll(acc)
    }

    override fun errorMessage(
        ctx: CastingEnvironment,
        errorCtx: Context
    ): Component =
        if (got == 0)
            error("for_each_no_args", expected)
        else
            error("for_each_not_enough_args", expected, got)
}