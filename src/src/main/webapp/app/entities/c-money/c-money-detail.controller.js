(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_moneyDetailController', C_moneyDetailController);

    C_moneyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_money'];

    function C_moneyDetailController($scope, $rootScope, $stateParams, entity, C_money) {
        var vm = this;
        vm.c_money = entity;
        vm.load = function (id) {
            C_money.get({id: id}, function(result) {
                vm.c_money = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_moneyUpdate', function(event, result) {
            vm.c_money = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
