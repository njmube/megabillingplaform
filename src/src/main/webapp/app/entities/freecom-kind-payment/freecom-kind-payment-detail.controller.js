(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_kind_paymentDetailController', Freecom_kind_paymentDetailController);

    Freecom_kind_paymentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_kind_payment', 'Free_cfdi'];

    function Freecom_kind_paymentDetailController($scope, $rootScope, $stateParams, entity, Freecom_kind_payment, Free_cfdi) {
        var vm = this;
        vm.freecom_kind_payment = entity;
        vm.load = function (id) {
            Freecom_kind_payment.get({id: id}, function(result) {
                vm.freecom_kind_payment = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_kind_paymentUpdate', function(event, result) {
            vm.freecom_kind_payment = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
