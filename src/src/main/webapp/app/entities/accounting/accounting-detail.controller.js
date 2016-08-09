(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('AccountingDetailController', AccountingDetailController);

    AccountingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Accounting', 'Freecom_ine_entity'];

    function AccountingDetailController($scope, $rootScope, $stateParams, entity, Accounting, Freecom_ine_entity) {
        var vm = this;
        vm.accounting = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:accountingUpdate', function(event, result) {
            vm.accounting = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
