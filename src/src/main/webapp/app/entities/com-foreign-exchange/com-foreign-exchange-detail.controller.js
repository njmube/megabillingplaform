(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_foreign_exchangeDetailController', Com_foreign_exchangeDetailController);

    Com_foreign_exchangeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_foreign_exchange', 'C_type_operation', 'Cfdi'];

    function Com_foreign_exchangeDetailController($scope, $rootScope, $stateParams, entity, Com_foreign_exchange, C_type_operation, Cfdi) {
        var vm = this;
        vm.com_foreign_exchange = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_foreign_exchangeUpdate', function(event, result) {
            vm.com_foreign_exchange = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
