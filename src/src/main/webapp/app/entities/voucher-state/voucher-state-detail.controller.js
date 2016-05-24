(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Voucher_stateDetailController', Voucher_stateDetailController);

    Voucher_stateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Voucher_state'];

    function Voucher_stateDetailController($scope, $rootScope, $stateParams, entity, Voucher_state) {
        var vm = this;
        vm.voucher_state = entity;
        vm.load = function (id) {
            Voucher_state.get({id: id}, function(result) {
                vm.voucher_state = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:voucher_stateUpdate', function(event, result) {
            vm.voucher_state = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
