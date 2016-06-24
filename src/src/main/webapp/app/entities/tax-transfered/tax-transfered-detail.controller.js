(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_transferedDetailController', Tax_transferedDetailController);

    Tax_transferedDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Tax_transfered', 'Tax_types', 'Rate_type'];

    function Tax_transferedDetailController($scope, $rootScope, $stateParams, entity, Tax_transfered, Tax_types, Rate_type) {
        var vm = this;
        vm.tax_transfered = entity;
        vm.load = function (id) {
            Tax_transfered.get({id: id}, function(result) {
                vm.tax_transfered = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:tax_transferedUpdate', function(event, result) {
            vm.tax_transfered = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
