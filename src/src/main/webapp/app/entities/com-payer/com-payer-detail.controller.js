(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_payerDetailController', Com_payerDetailController);

    Com_payerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_payer'];

    function Com_payerDetailController($scope, $rootScope, $stateParams, entity, Com_payer) {
        var vm = this;
        vm.com_payer = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_payerUpdate', function(event, result) {
            vm.com_payer = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
