(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_conceptDetailController', Tax_conceptDetailController);

    Tax_conceptDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Tax_concept', 'C_tax_rate'];

    function Tax_conceptDetailController($scope, $rootScope, $stateParams, entity, Tax_concept, C_tax_rate) {
        var vm = this;
        vm.tax_concept = entity;
        vm.load = function (id) {
            Tax_concept.get({id: id}, function(result) {
                vm.tax_concept = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:tax_conceptUpdate', function(event, result) {
            vm.tax_concept = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
