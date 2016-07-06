(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_tax_retentionsDetailController', Free_tax_retentionsDetailController);

    Free_tax_retentionsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Free_tax_retentions', 'Free_concept', 'Tax_types'];

    function Free_tax_retentionsDetailController($scope, $rootScope, $stateParams, entity, Free_tax_retentions, Free_concept, Tax_types) {
        var vm = this;
        vm.free_tax_retentions = entity;
        vm.load = function (id) {
            Free_tax_retentions.get({id: id}, function(result) {
                vm.free_tax_retentions = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:free_tax_retentionsUpdate', function(event, result) {
            vm.free_tax_retentions = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
