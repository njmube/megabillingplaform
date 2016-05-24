(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Voucher_typeDetailController', Voucher_typeDetailController);

    Voucher_typeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Voucher_type'];

    function Voucher_typeDetailController($scope, $rootScope, $stateParams, entity, Voucher_type) {
        var vm = this;
        vm.voucher_type = entity;
        vm.load = function (id) {
            Voucher_type.get({id: id}, function(result) {
                vm.voucher_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:voucher_typeUpdate', function(event, result) {
            vm.voucher_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
