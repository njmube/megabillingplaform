(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_foreign_tradeDetailController', Com_foreign_tradeDetailController);

    Com_foreign_tradeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_foreign_trade', 'Cfdi', 'Com_incoterm', 'C_type_operation_ce', 'C_key_pediment', 'Com_addressee'];

    function Com_foreign_tradeDetailController($scope, $rootScope, $stateParams, entity, Com_foreign_trade, Cfdi, Com_incoterm, C_type_operation_ce, C_key_pediment, Com_addressee) {
        var vm = this;
        vm.com_foreign_trade = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_foreign_tradeUpdate', function(event, result) {
            vm.com_foreign_trade = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
