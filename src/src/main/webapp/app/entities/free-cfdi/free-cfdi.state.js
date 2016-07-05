(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('free-cfdi', {
            parent: 'entity',
            url: '/free-cfdi?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.free_cfdi.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-cfdi/free-cfdis.html',
                    controller: 'Free_cfdiController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('free_cfdi');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
		.state('free-cfdi.new', {
            parent: 'free-cfdi',
            url: '/{login}/new',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.free_cfdi.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-cfdi/free-cfdi-new.html',
                    controller: 'Free_cfdiNewController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
				entity: [function () {
					return {
						version: null,
						serial: null,
						folio: null,
						date_expedition: null,
						payment_conditions: null,
						change_type: null,
						place_expedition: null,
						account_number: null,
						folio_fiscal_orig: null,
						serial_folio_fiscal_orig: null,
						date_folio_fiscal_orig: null,
						mont_folio_fiscal_orig: null,
						total_tax_retention: null,
						total_tax_transfered: null,
						discount: null,
						discount_reason: null,
						subtotal: null,
						total: null,
						addenda: null,
						stamp: null,
						no_certificate: null,
						certificate: null,
						id: null
					};
				}],
				free_emitter_entity: ['$stateParams', 'Free_emitter', function($stateParams, Free_emitter) {
					return Free_emitter.get({login : $stateParams.login});
				}],
				user: ['$stateParams', 'User',  function($stateParams, User) {
					return User.get({login : $stateParams.login});
                }],
				free_receiver_entity: [function () {
					return {
						rfc: null,
						business_name: null,
						email: null,
						activated: false,
						create_date: null,
						street: null,
						no_ext: null,
						no_int: null,
						reference: null,
						id: null
					};
				}],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('free_cfdi');
					$translatePartialLoader.addPart('free_emitter');
					$translatePartialLoader.addPart('free_receiver');
					$translatePartialLoader.addPart('free_concept');
					$translatePartialLoader.addPart('free_customs_info');
					$translatePartialLoader.addPart('free_part_concept');
					$translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('free-cfdi-detail', {
            parent: 'entity',
            url: '/free-cfdi/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.free_cfdi.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-cfdi/free-cfdi-detail.html',
                    controller: 'Free_cfdiDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('free_cfdi');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Free_cfdi', function($stateParams, Free_cfdi) {
                    return Free_cfdi.get({id : $stateParams.id});
                }]
            }
        })
        
        .state('free-cfdi.edit', {
            parent: 'free-cfdi',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-cfdi/free-cfdi-dialog.html',
                    controller: 'Free_cfdiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Free_cfdi', function(Free_cfdi) {
                            return Free_cfdi.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-cfdi', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('free-cfdi.delete', {
            parent: 'free-cfdi',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-cfdi/free-cfdi-delete-dialog.html',
                    controller: 'Free_cfdiDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Free_cfdi', function(Free_cfdi) {
                            return Free_cfdi.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-cfdi', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
