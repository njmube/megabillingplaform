(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_foreign_tradeDetailController', Freecom_foreign_tradeDetailController);

    Freecom_foreign_tradeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_foreign_trade', 'Free_cfdi', 'Freecom_incoterm', 'C_type_operation_ce', 'C_key_pediment', 'Freecom_addressee'];

    function Freecom_foreign_tradeDetailController($scope, $rootScope, $stateParams, entity, Freecom_foreign_trade, Free_cfdi, Freecom_incoterm, C_type_operation_ce, C_key_pediment, Freecom_addressee) {
        var vm = this;
        vm.freecom_foreign_trade = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_foreign_tradeUpdate', function(event, result) {
            vm.freecom_foreign_trade = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
