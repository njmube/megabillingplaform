(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_conceptDetailController', Taxpayer_conceptDetailController);

    Taxpayer_conceptDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Taxpayer_concept', 'Taxpayer_account', 'Measure_unit_concept', 'Price_concept', 'Tax_concept', 'Discount_concept'];

    function Taxpayer_conceptDetailController($scope, $rootScope, $stateParams, entity, Taxpayer_concept, Taxpayer_account, Measure_unit_concept, Price_concept, Tax_concept, Discount_concept) {
        var vm = this;
        vm.taxpayer_concept = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:taxpayer_conceptUpdate', function(event, result) {
            vm.taxpayer_concept = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
