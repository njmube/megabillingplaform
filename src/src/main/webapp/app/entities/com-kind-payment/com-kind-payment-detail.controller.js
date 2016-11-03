(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_kind_paymentDetailController', Com_kind_paymentDetailController);

    Com_kind_paymentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_kind_payment', 'Cfdi'];

    function Com_kind_paymentDetailController($scope, $rootScope, $stateParams, entity, Com_kind_payment, Cfdi) {
        var vm = this;
        vm.com_kind_payment = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_kind_paymentUpdate', function(event, result) {
            vm.com_kind_payment = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
