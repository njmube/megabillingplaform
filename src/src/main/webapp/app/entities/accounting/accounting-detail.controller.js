(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('AccountingDetailController', AccountingDetailController);

    AccountingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Accounting', 'Entity_cfdi'];

    function AccountingDetailController($scope, $rootScope, $stateParams, entity, Accounting, Entity_cfdi) {
        var vm = this;
        vm.accounting = entity;
        vm.load = function (id) {
            Accounting.get({id: id}, function(result) {
                vm.accounting = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:accountingUpdate', function(event, result) {
            vm.accounting = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
