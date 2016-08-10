(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_payerDetailController', Freecom_payerDetailController);

    Freecom_payerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_payer'];

    function Freecom_payerDetailController($scope, $rootScope, $stateParams, entity, Freecom_payer) {
        var vm = this;

        vm.freecom_payer = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_payerUpdate', function(event, result) {
            vm.freecom_payer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
