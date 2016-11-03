(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_accountingDetailController', Com_accountingDetailController);

    Com_accountingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_accounting', 'Com_ine_entity'];

    function Com_accountingDetailController($scope, $rootScope, $stateParams, entity, Com_accounting, Com_ine_entity) {
        var vm = this;
        vm.com_accounting = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_accountingUpdate', function(event, result) {
            vm.com_accounting = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
