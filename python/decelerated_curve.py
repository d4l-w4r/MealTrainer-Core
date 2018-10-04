import utils


class DeceleratedCurve():

    def __init__(self, config):
        self.config = config
        self.b, _ = self.__find_b_for_minimal_target_difference(
            config.target_weight, config.duration)
        self.a = self.__get_deceleration(self.b, config.duration)
        self.default_time_space = utils.construct_value_space(
            0, config.duration)

    def get_quadratic_curve_shape(self, time_space=None):
        time = self.default_time_space if time_space != None else time_space
        return list(map(lambda x: self.__estimate_weight_consumed_at_time(x), time))

    def get_time_till_next_bite(self, current_time_secs):
        x = utils.to_decimal_minutes(current_time_secs)
        estimate_food_consumed = self.__estimate_weight_consumed_at_time(x)
        food_consumed_1_min_ahead = self.__estimate_weight_consumed_at_time(
            x + 1)
        consumption_delta = food_consumed_1_min_ahead - estimate_food_consumed
        bites_needed = max(consumption_delta / self.config.weight_per_bite, 1)
        time_per_bite = 60.0 / bites_needed
        return time_per_bite

    def __find_b_for_minimal_target_difference(self, target, duration):
        B = utils.construct_value_space(5, 160)
        diffs = list(map(lambda b: abs(
            target - (self.__get_deceleration(b, duration) * duration ** 2 + b * duration)), B))
        min_diff = min(diffs)
        if min_diff > 1:
            print("**********************************************************")
            print("[WARNING]: Can't estimate the target weight to within 1g.\n\t   Minimum absolute difference to target weight '{0}g' for duration '{1} min' is {2}g.".format(
                target, duration, min_diff))
            print("**********************************************************")
        return B[diffs.index(min_diff)], min_diff

    def __estimate_weight_consumed_at_time(self, x):
        return (self.a * x ** 2) + (self.b * x)

    def __get_deceleration(self, eating_rate, duration):
        return -(eating_rate/(2*duration))
