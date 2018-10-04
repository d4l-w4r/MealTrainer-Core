import numpy as np


def construct_value_space(min_val, max_val, vals_between_ints=100):
    return np.linspace(min_val, max_val, (max_val - min_val) * vals_between_ints)


def to_decimal_minutes(seconds):
    return seconds / 60.0
