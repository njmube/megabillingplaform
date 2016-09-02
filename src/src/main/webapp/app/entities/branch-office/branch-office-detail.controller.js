(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Branch_officeDetailController', Branch_officeDetailController);

    Branch_officeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Branch_office', 'Tax_address', 'Taxpayer_account'];

    function Branch_officeDetailController($scope, $rootScope, $stateParams, entity, Branch_office, Tax_address, Taxpayer_account) {
        var vm = this;

        vm.branch_office = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:branch_officeUpdate', function(event, result) {
            vm.branch_office = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
