(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_transferedDetailController', Tax_transferedDetailController);

    Tax_transferedDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Tax_transfered', 'Tax_types'];

    function Tax_transferedDetailController($scope, $rootScope, $stateParams, entity, Tax_transfered, Tax_types) {
        var vm = this;
        vm.tax_transfered = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:tax_transferedUpdate', function(event, result) {
            vm.tax_transfered = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
