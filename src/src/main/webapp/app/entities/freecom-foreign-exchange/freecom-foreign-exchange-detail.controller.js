(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_foreign_exchangeDetailController', Freecom_foreign_exchangeDetailController);

    Freecom_foreign_exchangeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_foreign_exchange', 'C_type_operation', 'Free_cfdi'];

    function Freecom_foreign_exchangeDetailController($scope, $rootScope, $stateParams, entity, Freecom_foreign_exchange, C_type_operation, Free_cfdi) {
        var vm = this;
        vm.freecom_foreign_exchange = entity;
        vm.load = function (id) {
            Freecom_foreign_exchange.get({id: id}, function(result) {
                vm.freecom_foreign_exchange = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_foreign_exchangeUpdate', function(event, result) {
            vm.freecom_foreign_exchange = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
