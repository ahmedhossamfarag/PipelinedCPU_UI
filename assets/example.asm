addi ax, ax, 100
addi bx, bx, 12
addi cx, cx, -5
addi dx, dx, 20
lw cx, 0(ds)
lw es, 5(ds)
add ax, ax, bx
sw bx, 3(ds)
j l1
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
noop
noop
l1: and dx, ax, bx
lw sp, 3(ds)
noop
noop
noop
noop
