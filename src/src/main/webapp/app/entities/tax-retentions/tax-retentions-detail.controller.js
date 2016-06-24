(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_retentionsDetailController', Tax_retentionsDetailController);

    Tax_retentionsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Tax_retentions', 'Tax_types'];

    function Tax_retentionsDetailController($scope, $rootScope, $stateParams, entity, Tax_retentions, Tax_types) {
        var vm = this;
        vm.tax_retentions = entity;
        vm.load = function (id) {
            Tax_retentions.get({id: id}, function(result) {
                vm.tax_retentions = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:tax_retentionsUpdate', function(event, result) {
            vm.tax_retentions = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
