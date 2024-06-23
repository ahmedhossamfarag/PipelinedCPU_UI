addi ax, ax, 100
addi bx, bx, 12
lw cx, 0(ds)
lw es, 5(ds)
noop
noop
add ax, ax, bx
sw bx, 3(ds)
j l1
addi cx, cx, 30
addi bx, bx, 87
add cx, cx, bx
noop
noop
noop
noop
noop
noop
noop
noop
noop
noop
noop
noop
l1: and dx, ax, bx
lw sp, 3(ds)
beq cx, dx, l2
addi cx, cx, 30
addi bx, bx, 87
add cx, cx, bx
noop
noop
noop
noop
l2: noop
noop
noop
noop
noop